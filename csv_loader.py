import pandas as pd
import os


class CSVLoader:

    def __init__(self, file_path):

        self.file_path = file_path
        self.data = None

    # =========================================================
    # VALIDATE FILE
    # =========================================================

    def validate_file(self):

        if not self.file_path:

            raise ValueError(
                "No file path provided."
            )

        if not os.path.exists(
                self.file_path):

            raise FileNotFoundError(
                "CSV file not found."
            )

        if not self.file_path.lower().endswith(
                ".csv"):

            raise ValueError(
                "Only CSV files are supported."
            )

    # =========================================================
    # LOAD DATA
    # =========================================================

    def load_data(self):

        self.validate_file()

        try:

            self.data = pd.read_csv(
                self.file_path
            )

        except Exception as e:

            raise ValueError(
                f"Unable to read CSV file: {e}"
            )

        if self.data.empty:

            raise ValueError(
                "The CSV file is empty."
            )

        return self.data

    # =========================================================
    # DATASET INFO
    # =========================================================

    def get_dataset_info(self):

        if self.data is None:

            raise ValueError(
                "Dataset has not been loaded yet."
            )

        return {

            "rows":
                int(self.data.shape[0]),

            "columns":
                int(self.data.shape[1]),

            "column_names":
                self.data.columns.tolist(),

            "data_types": {

                column: str(dtype)

                for column, dtype
                in self.data.dtypes.items()
            }
        }