import os
import base64
from datetime import datetime


class ReportGenerator:

    def __init__(self, dataset_name, cleaning_result, stats_summary, chart_files, output_dir):
        self.dataset_name = dataset_name
        self.cleaning_result = cleaning_result
        self.stats_summary = stats_summary
        self.chart_files = chart_files
        self.output_dir = output_dir

    def _encode_image(self, path):
        try:
            with open(path, "rb") as f:
                encoded = base64.b64encode(f.read()).decode("utf-8")
            return f"data:image/png;base64,{encoded}"
        except Exception:
            return ""

    def _build_table(self, headers, rows):

        html = "<table class='data-table'><thead><tr>"
        for h in headers:
            html += f"<th>{h}</th>"
        html += "</tr></thead><tbody>"

        for row in rows:
            html += "<tr>" + "".join(f"<td>{cell}</td>" for cell in row) + "</tr>"

        html += "</tbody></table>"
        return html

    def generate(self):

        os.makedirs(self.output_dir, exist_ok=True)

        cleaning_actions_html = "".join(
            "<li>{action}{column}{count}</li>".format(
                action=a.get("action", ""),
                column=(" — " + str(a.get("column"))) if a.get("column") else "",
                count=(" (" + str(a.get("count")) + " affected)") if a.get("count") else ""
            )
            for a in self.cleaning_result.get("cleaning_actions", [])
        )

        numeric_rows = [
            [col, s["count"], s["mean"], s["median"], s["std"], s["min"], s["max"]]
            for col, s in self.stats_summary.get("numeric_summary", {}).items()
        ]

        categorical_rows = [
            [col, s["unique_count"], s["top_value"], s["top_frequency"]]
            for col, s in self.stats_summary.get("categorical_summary", {}).items()
        ]

        chart_html = ""
        for chart in self.chart_files:
            img_data = self._encode_image(chart["path"])
            if img_data:
                chart_html += (
                    f'<div class="chart-block"><h3>{chart["title"]}</h3>'
                    f'<img src="{img_data}" /></div>'
                )

        missing_total_before = sum(self.cleaning_result.get("missing_values_found", {}).values())

        html = f"""<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Data Cleaning &amp; Analysis Report</title>
<style>
    body {{ font-family: 'Segoe UI', Arial, sans-serif; background:#f5f7fa; color:#1f2937; margin:0; padding:30px; }}
    h1 {{ color:#111827; }}
    h2 {{ border-bottom: 2px solid #2563eb; padding-bottom:6px; color:#1e3a8a; margin-top:40px; }}
    .meta {{ color:#6b7280; margin-bottom:20px; }}
    .cards {{ display:flex; gap:15px; margin:20px 0; flex-wrap:wrap; }}
    .card {{ background:white; border:1px solid #e5e7eb; border-radius:8px; padding:18px 22px; min-width:150px; }}
    .card .label {{ font-size:13px; color:#6b7280; }}
    .card .value {{ font-size:26px; font-weight:bold; color:#111827; }}
    table.data-table {{ border-collapse:collapse; width:100%; margin:15px 0; background:white; }}
    table.data-table th, table.data-table td {{ border:1px solid #e5e7eb; padding:8px 12px; text-align:left; font-size:14px; }}
    table.data-table th {{ background:#f3f4f6; }}
    .chart-block {{ background:white; border:1px solid #e5e7eb; border-radius:8px; padding:15px; margin:15px 0; }}
    .chart-block img {{ max-width:100%; border-radius:4px; }}
    ul {{ background:white; border:1px solid #e5e7eb; border-radius:8px; padding:20px 40px; }}
</style>
</head>
<body>
    <h1>Data Cleaning &amp; Analysis Report</h1>
    <div class="meta">
        Dataset: {self.dataset_name}<br>
        Generated: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}
    </div>

    <h2>Dataset Overview</h2>
    <div class="cards">
        <div class="card"><div class="label">Original Rows</div><div class="value">{self.cleaning_result.get('original_rows')}</div></div>
        <div class="card"><div class="label">Cleaned Rows</div><div class="value">{self.cleaning_result.get('cleaned_rows')}</div></div>
        <div class="card"><div class="label">Duplicates Removed</div><div class="value">{self.cleaning_result.get('duplicate_rows_removed')}</div></div>
        <div class="card"><div class="label">Missing Values Fixed</div><div class="value">{missing_total_before}</div></div>
    </div>

    <h2>Cleaning Actions Performed</h2>
    <ul>{cleaning_actions_html or "<li>No cleaning actions were needed.</li>"}</ul>

    <h2>Numeric Column Statistics</h2>
    {self._build_table(["Column", "Count", "Mean", "Median", "Std Dev", "Min", "Max"], numeric_rows) if numeric_rows else "<p>No numeric columns found.</p>"}

    <h2>Categorical Column Statistics</h2>
    {self._build_table(["Column", "Unique Values", "Most Common", "Frequency"], categorical_rows) if categorical_rows else "<p>No categorical columns found.</p>"}

    <h2>Charts</h2>
    {chart_html or "<p>No charts generated.</p>"}

</body>
</html>"""

        report_path = os.path.join(self.output_dir, "data_report.html")

        with open(report_path, "w", encoding="utf-8") as f:
            f.write(html)

        return report_path