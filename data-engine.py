import sys
import os
import json
import warnings

warnings.filterwarnings("ignore")

from loader.csv_loader import CSVLoader
from cleaning.cleaner import DataCleaner
from analysis.stats_engine import StatisticsEngine
from charts.chart_engine import ChartEngine
from report.report_generator import ReportGenerator


# =========================================================
# LOAD + CLEAN (shared by every action)
# =========================================================

def load_and_clean(csv_file_path):

    loader = CSVLoader(csv_file_path)
    original_data = loader.load_data()
    info = loader.get_dataset_info()

    cleaner = DataCleaner(original_data)
    result = cleaner.clean()

    base_path, _ext = os.path.splitext(csv_file_path)
    cleaned_file_path = base_path + "_cleaned.csv"

    cleaner.data.to_csv(cleaned_file_path, index=False)

    return original_data, info, cleaner, result, cleaned_file_path, base_path


def build_clean_output(info, result, cleaned_file_path):

    return {
        "rows": info["rows"],
        "columns": info["columns"],
        "column_names": info["column_names"],

        "original_rows": result["original_rows"],
        "cleaned_rows": result["cleaned_rows"],
        "original_columns": result["original_columns"],
        "cleaned_columns": result["cleaned_columns"],

        "duplicate_rows": result["duplicate_rows_found"],
        "duplicates_removed": result["duplicate_rows_removed"],
        "remaining_duplicates": result["remaining_duplicate_rows"],

        "missing_values_before": result["missing_values_found"],
        "missing_value_total_before": result["missing_values_found_total"],

        "missing_values": result["missing_values"],
        "missing_value_total": result["missing_value_total"],

        "date_columns": result["date_columns"],
        "cleaning_actions": result["cleaning_actions"],

        "cleaned_file_path": cleaned_file_path
    }


# =========================================================
# ACTION: CLEAN
# =========================================================

def run_clean(csv_file_path):

    original_data, info, cleaner, result, cleaned_file_path, base_path = load_and_clean(csv_file_path)

    output = {"success": True}
    output.update(build_clean_output(info, result, cleaned_file_path))

    print(json.dumps(output, default=str))


# =========================================================
# ACTION: STATISTICS
# =========================================================

def run_stats(csv_file_path):

    original_data, info, cleaner, result, cleaned_file_path, base_path = load_and_clean(csv_file_path)

    stats_engine = StatisticsEngine(cleaner.data)
    stats_summary = stats_engine.get_full_summary()

    output = {"success": True}
    output.update(build_clean_output(info, result, cleaned_file_path))
    output.update(stats_summary)

    print(json.dumps(output, default=str))


# =========================================================
# ACTION: CHARTS
# =========================================================

def run_charts(csv_file_path):

    original_data, info, cleaner, result, cleaned_file_path, base_path = load_and_clean(csv_file_path)

    charts_dir = base_path + "_charts"
    chart_engine = ChartEngine(cleaner.data, charts_dir)
    chart_files = chart_engine.generate_all(original_data=original_data)

    output = {"success": True, "chart_files": chart_files}
    output.update(build_clean_output(info, result, cleaned_file_path))

    print(json.dumps(output, default=str))


# =========================================================
# ACTION: REPORT (stats + charts + HTML report, all combined)
# =========================================================

def run_report(csv_file_path):

    original_data, info, cleaner, result, cleaned_file_path, base_path = load_and_clean(csv_file_path)

    stats_engine = StatisticsEngine(cleaner.data)
    stats_summary = stats_engine.get_full_summary()

    charts_dir = base_path + "_charts"
    chart_engine = ChartEngine(cleaner.data, charts_dir)
    chart_files = chart_engine.generate_all(original_data=original_data)

    report_generator = ReportGenerator(
        dataset_name=os.path.basename(csv_file_path),
        cleaning_result=result,
        stats_summary=stats_summary,
        chart_files=chart_files,
        output_dir=base_path + "_report_output"
    )

    report_path = report_generator.generate()

    output = {"success": True, "report_file": report_path, "chart_files": chart_files}
    output.update(build_clean_output(info, result, cleaned_file_path))
    output.update(stats_summary)

    print(json.dumps(output, default=str))


# =========================================================
# MAIN
# =========================================================

def main():

    if len(sys.argv) < 2:
        print(json.dumps({"success": False, "error": "CSV file path was not provided."}))
        return

    csv_file_path = sys.argv[1]
    action = sys.argv[2] if len(sys.argv) > 2 else "clean"

    try:

        if action == "stats":
            run_stats(csv_file_path)
        elif action == "charts":
            run_charts(csv_file_path)
        elif action == "report":
            run_report(csv_file_path)
        else:
            run_clean(csv_file_path)

    except Exception as e:
        print(json.dumps({"success": False, "error": str(e)}))


if __name__ == "__main__":
    main()