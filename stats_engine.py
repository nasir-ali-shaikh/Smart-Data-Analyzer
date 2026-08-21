import pandas as pd
import numpy as np


class StatisticsEngine:

    def __init__(self, data):
        self.data = data

    # ==========================================
    # NUMERIC COLUMN STATISTICS
    # ==========================================

    def numeric_summary(self):

        summary = {}

        numeric_columns = self.data.select_dtypes(include=[np.number]).columns

        for column in numeric_columns:

            series = self.data[column].dropna()

            if series.empty:
                continue

            summary[column] = {
                "count": int(series.count()),
                "mean": round(float(series.mean()), 2),
                "median": round(float(series.median()), 2),
                "std": round(float(series.std()), 2) if series.count() > 1 else 0.0,
                "min": round(float(series.min()), 2),
                "max": round(float(series.max()), 2),
                "q1": round(float(series.quantile(0.25)), 2),
                "q3": round(float(series.quantile(0.75)), 2),
            }

        return summary

    # ==========================================
    # CATEGORICAL / TEXT COLUMN STATISTICS
    # ==========================================

    def categorical_summary(self):

        summary = {}

        categorical_columns = self.data.select_dtypes(exclude=[np.number]).columns

        for column in categorical_columns:

            series = self.data[column].dropna()

            if series.empty:
                continue

            value_counts = series.astype(str).value_counts()

            top_value = value_counts.index[0] if len(value_counts) > 0 else ""
            top_freq = int(value_counts.iloc[0]) if len(value_counts) > 0 else 0

            summary[column] = {
                "unique_count": int(series.nunique()),
                "top_value": str(top_value),
                "top_frequency": top_freq,
                "non_null_count": int(series.count()),
            }

        return summary

    # ==========================================
    # STRONGEST NUMERIC CORRELATIONS
    # ==========================================

    def correlation_summary(self, top_n=5):

        numeric_data = self.data.select_dtypes(include=[np.number])

        if numeric_data.shape[1] < 2:
            return []

        corr_matrix = numeric_data.corr()

        pairs = []
        seen = set()

        for col1 in corr_matrix.columns:
            for col2 in corr_matrix.columns:

                if col1 == col2:
                    continue

                key = tuple(sorted([col1, col2]))

                if key in seen:
                    continue

                seen.add(key)

                value = corr_matrix.loc[col1, col2]

                if pd.isna(value):
                    continue

                pairs.append({
                    "column_a": col1,
                    "column_b": col2,
                    "correlation": round(float(value), 3)
                })

        pairs.sort(key=lambda p: abs(p["correlation"]), reverse=True)

        return pairs[:top_n]

    # ==========================================
    # FULL SUMMARY
    # ==========================================

    def get_full_summary(self):

        return {
            "row_count": int(len(self.data)),
            "column_count": int(self.data.shape[1]),
            "numeric_summary": self.numeric_summary(),
            "categorical_summary": self.categorical_summary(),
            "top_correlations": self.correlation_summary(),
        }