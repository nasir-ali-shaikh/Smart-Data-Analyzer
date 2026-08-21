import os
import warnings

warnings.filterwarnings("ignore")

import matplotlib
matplotlib.use("Agg")  # no display needed, just save PNG files

import matplotlib.pyplot as plt
import seaborn as sns

sns.set_theme(style="whitegrid")


class ChartEngine:

    def __init__(self, data, output_dir):
        self.data = data
        self.output_dir = output_dir
        os.makedirs(self.output_dir, exist_ok=True)

    def _save(self, fig, filename):
        path = os.path.join(self.output_dir, filename)
        fig.savefig(path, dpi=110, bbox_inches="tight")
        plt.close(fig)
        return path

    # ==========================================
    # MISSING VALUES CHART (before cleaning)
    # ==========================================

    def generate_missing_values_chart(self, original_data):

        missing = original_data.isnull().sum()
        missing = missing[missing > 0]

        if missing.empty:
            return []

        fig, ax = plt.subplots(figsize=(6, 4))
        sns.barplot(x=missing.values, y=missing.index, ax=ax, color="#dc2626")
        ax.set_title("Missing Values by Column (Before Cleaning)")
        ax.set_xlabel("Missing Count")
        ax.set_ylabel("Column")

        path = self._save(fig, "missing_values_before.png")
        return [{"title": "Missing Values (Before Cleaning)", "path": path}]

    # ==========================================
    # HISTOGRAMS FOR NUMERIC COLUMNS
    # ==========================================

    def generate_histograms(self, max_columns=4):

        charts = []
        numeric_columns = list(self.data.select_dtypes(include="number").columns)[:max_columns]

        for column in numeric_columns:

            series = self.data[column].dropna()
            if series.empty:
                continue

            fig, ax = plt.subplots(figsize=(6, 4))
            sns.histplot(series, kde=True, ax=ax, color="#2563eb")
            ax.set_title(f"Distribution of {column}")
            ax.set_xlabel(column)
            ax.set_ylabel("Frequency")

            path = self._save(fig, f"hist_{column}.png")
            charts.append({"title": f"Distribution: {column}", "path": path})

        return charts

    # ==========================================
    # BAR CHARTS FOR CATEGORICAL COLUMNS
    # ==========================================

    def generate_bar_charts(self, max_columns=2, top_n=8):

        charts = []
        categorical_columns = list(self.data.select_dtypes(exclude="number").columns)[:max_columns]

        for column in categorical_columns:

            value_counts = self.data[column].astype(str).value_counts().head(top_n)

            if value_counts.empty:
                continue

            fig, ax = plt.subplots(figsize=(6, 4))
            sns.barplot(
                x=value_counts.values, y=value_counts.index,
                hue=value_counts.index, palette="Blues_r", legend=False, ax=ax
            )
            ax.set_title(f"Top Values in {column}")
            ax.set_xlabel("Count")
            ax.set_ylabel(column)

            path = self._save(fig, f"bar_{column}.png")
            charts.append({"title": f"Top Values: {column}", "path": path})

        return charts

    # ==========================================
    # CORRELATION HEATMAP
    # ==========================================

    def generate_correlation_heatmap(self):

        numeric_data = self.data.select_dtypes(include="number")

        if numeric_data.shape[1] < 2:
            return []

        fig, ax = plt.subplots(figsize=(6, 5))
        sns.heatmap(numeric_data.corr(), annot=True, cmap="Blues", fmt=".2f", ax=ax)
        ax.set_title("Correlation Heatmap")

        path = self._save(fig, "correlation_heatmap.png")
        return [{"title": "Correlation Heatmap", "path": path}]

    # ==========================================
    # GENERATE ALL CHARTS
    # ==========================================

    def generate_all(self, original_data=None):

        charts = []
        charts += self.generate_missing_values_chart(
            original_data if original_data is not None else self.data
        )
        charts += self.generate_histograms()
        charts += self.generate_bar_charts()
        charts += self.generate_correlation_heatmap()

        return charts