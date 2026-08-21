import warnings
import pandas as pd
import numpy as np

warnings.filterwarnings("ignore", category=UserWarning)


class DataCleaner:

    # Common "fake" representations of missing data found in raw CSVs
    FAKE_NULLS = [
        "na", "n/a", "n\\a", "null", "none", "nil",
        "-", "--", "?", "unknown", "not available", ""
    ]

    def __init__(self, data):
        self.data = data.copy()
        self.original_data = data.copy()
        self.cleaning_report = []

    # ==========================================
    # HELPER: TEXT COLUMN DETECTION
    # ==========================================

    def _is_text_column(self, series):
        """
        Works across pandas versions: classic 'object' dtype AND
        the newer pandas StringDtype ('str' / 'string') both count
        as text columns.
        """

        return (
            pd.api.types.is_object_dtype(series)
            or pd.api.types.is_string_dtype(series)
        )

    # ==========================================
    # STEP 0: NORMALIZE FAKE-NULL TEXT VALUES
    # ==========================================

    def normalize_fake_nulls(self):
        """
        Converts things like 'NA', 'null', '-', '?' etc. (typed as
        text in the CSV) into real missing values (NaN) so they get
        picked up and handled properly later.
        """

        changed_columns = []

        for column in self.data.columns:

            if self._is_text_column(self.data[column]):

                before_missing = int(self.data[column].isnull().sum())

                self.data[column] = self.data[column].apply(
                    lambda v: np.nan
                    if isinstance(v, str) and v.strip().lower() in self.FAKE_NULLS
                    else v
                )

                after_missing = int(self.data[column].isnull().sum())

                if after_missing > before_missing:
                    changed_columns.append(column)

        if changed_columns:
            self.cleaning_report.append({
                "action": "Fake Null Text Normalized",
                "columns": ", ".join(changed_columns)
            })

        return changed_columns

    # ==========================================
    # STEP 0.5: TRIM WHITESPACE / CLEAN TEXT
    # ==========================================

    def trim_whitespace(self):
        """
        Strips leading/trailing spaces and collapses multiple
        internal spaces in every text column.
        """

        trimmed_columns = []

        for column in self.data.columns:

            if self._is_text_column(self.data[column]):

                original = self.data[column].copy()

                self.data[column] = self.data[column].apply(
                    lambda v: " ".join(v.split()) if isinstance(v, str) else v
                )

                if not self.data[column].equals(original):
                    trimmed_columns.append(column)

        if trimmed_columns:
            self.cleaning_report.append({
                "action": "Whitespace Trimmed",
                "columns": ", ".join(trimmed_columns)
            })

        return trimmed_columns

    # ==========================================
    # STEP 0.6: CLEAN COLUMN NAMES
    # ==========================================

    def clean_column_names(self):
        """
        Strips whitespace from column headers themselves.
        """

        original_columns = list(self.data.columns)
        cleaned_columns = [str(c).strip() for c in original_columns]

        if original_columns != cleaned_columns:
            self.data.columns = cleaned_columns
            self.original_data.columns = cleaned_columns

            self.cleaning_report.append({
                "action": "Column Names Cleaned",
                "count": len(original_columns)
            })

        return cleaned_columns

    # ==========================================
    # MISSING VALUES (DETECTION)
    # ==========================================

    def detect_missing_values(self):

        missing = self.data.isnull().sum()

        result = {}

        for column, count in missing.items():

            if count > 0:
                result[column] = int(count)

        return result

    # ==========================================
    # DUPLICATES
    # ==========================================

    def detect_duplicates(self):

        return int(self.data.duplicated().sum())

    # ==========================================
    # DATA TYPES
    # ==========================================

    def detect_data_types(self):

        return {
            column: str(dtype)
            for column, dtype in self.data.dtypes.items()
        }

    # ==========================================
    # DATE COLUMNS
    # ==========================================

    def detect_date_columns(self):

        date_columns = []

        for column in self.data.columns:

            if self._is_text_column(self.data[column]):

                try:

                    converted = pd.to_datetime(
                        self.data[column],
                        errors="coerce"
                    )

                    valid_values = converted.notna().sum()

                    if len(self.data) > 0:

                        ratio = (
                            valid_values /
                            len(self.data)
                        )

                        if ratio >= 0.8:
                            date_columns.append(column)

                except Exception:
                    pass

        return date_columns

    # ==========================================
    # REMOVE DUPLICATES
    # ==========================================

    def remove_duplicates(self):

        duplicate_count = self.detect_duplicates()

        if duplicate_count > 0:

            self.data = (
                self.data
                .drop_duplicates()
                .reset_index(drop=True)
            )

            self.cleaning_report.append({
                "action": "Duplicate Rows Removed",
                "count": duplicate_count
            })

        return duplicate_count

    # ==========================================
    # STANDARDIZE DATES
    # ==========================================

    def standardize_dates(self):

        date_columns = self.detect_date_columns()

        for column in date_columns:

            try:

                self.data[column] = pd.to_datetime(
                    self.data[column],
                    errors="coerce"
                )

                self.cleaning_report.append({
                    "action": "Date Format Standardized",
                    "column": column
                })

            except Exception:
                pass

        return date_columns

    # ==========================================
    # AUTOMATICALLY FILL MISSING VALUES
    # ==========================================

    def handle_missing_values(self):
        """
        Fills missing values automatically:
        - Numeric columns  -> filled with the column median
        - Date columns     -> filled with forward/backward fill
        - Text/object cols -> filled with the most frequent value (mode),
                               or "Unknown" if the whole column is empty
        """

        for column in self.data.columns:

            missing_count = int(self.data[column].isnull().sum())

            if missing_count == 0:
                continue

            # ---- Numeric columns ----
            if pd.api.types.is_numeric_dtype(self.data[column]):

                fill_value = self.data[column].median()

                if pd.isna(fill_value):
                    fill_value = 0

                self.data[column] = self.data[column].fillna(fill_value)

                self.cleaning_report.append({
                    "action": "Missing Values Filled (Median)",
                    "column": column,
                    "count": missing_count,
                    "fill_value": str(fill_value)
                })

            # ---- Date columns ----
            elif pd.api.types.is_datetime64_any_dtype(self.data[column]):

                self.data[column] = (
                    self.data[column]
                    .ffill()
                    .bfill()
                )

                self.cleaning_report.append({
                    "action": "Missing Dates Filled (Forward/Backward Fill)",
                    "column": column,
                    "count": missing_count
                })

            # ---- Text / categorical columns ----
            else:

                mode_values = self.data[column].mode(dropna=True)

                if not mode_values.empty:
                    fill_value = mode_values.iloc[0]
                else:
                    fill_value = "Unknown"

                self.data[column] = self.data[column].fillna(fill_value)

                self.cleaning_report.append({
                    "action": "Missing Values Filled (Most Frequent)",
                    "column": column,
                    "count": missing_count,
                    "fill_value": str(fill_value)
                })

        return self.detect_missing_values()

    # ==========================================
    # REMOVE FULLY EMPTY ROWS/COLUMNS
    # ==========================================

    def remove_empty_rows_and_columns(self):

        rows_before = len(self.data)
        cols_before = self.data.shape[1]

        self.data = self.data.dropna(how="all").reset_index(drop=True)
        self.data = self.data.dropna(axis=1, how="all")

        rows_removed = rows_before - len(self.data)
        cols_removed = cols_before - self.data.shape[1]

        if rows_removed > 0:
            self.cleaning_report.append({
                "action": "Fully Empty Rows Removed",
                "count": rows_removed
            })

        if cols_removed > 0:
            self.cleaning_report.append({
                "action": "Fully Empty Columns Removed",
                "count": cols_removed
            })

        return rows_removed, cols_removed

    # ==========================================
    # CLEANING SUMMARY
    # ==========================================

    def get_cleaning_summary(self):

        return {

            "original_rows": int(
                len(self.original_data)
            ),

            "cleaned_rows": int(
                len(self.data)
            ),

            "original_columns": int(
                self.original_data.shape[1]
            ),

            "cleaned_columns": int(
                self.data.shape[1]
            ),

            "missing_values": self.detect_missing_values(),

            "missing_value_total": int(
                self.data.isnull().sum().sum()
            ),

            "duplicate_rows_removed": int(
                len(self.original_data)
                - len(self.data)
            ),

            "remaining_duplicate_rows": int(
                self.data.duplicated().sum()
            ),

            "data_types": self.detect_data_types(),

            "date_columns": self.detect_date_columns(),

            "cleaning_actions": self.cleaning_report
        }

    # ==========================================
    # MAIN CLEANING FUNCTION — cleans EVERYTHING
    # ==========================================

    def clean(self):

        # ---- BEFORE stats (for reporting) ----
        duplicates_found = self.detect_duplicates()
        missing_found = self.detect_missing_values()
        missing_found_total = int(sum(missing_found.values()))

        # ---- Full cleaning pipeline ----
        self.clean_column_names()
        self.normalize_fake_nulls()
        self.trim_whitespace()
        self.remove_duplicates()
        self.remove_empty_rows_and_columns()
        self.standardize_dates()
        self.handle_missing_values()

        # duplicates can reappear after trimming/normalizing text,
        # so remove them one more time to be safe
        self.remove_duplicates()

        summary = self.get_cleaning_summary()

        summary["duplicate_rows_found"] = duplicates_found
        summary["missing_values_found"] = missing_found
        summary["missing_values_found_total"] = missing_found_total

        return summary