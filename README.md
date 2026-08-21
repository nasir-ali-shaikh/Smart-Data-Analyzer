# 🚀 SmartData Analyzer

<p align="center">
  <b>A Desktop-Based Automated Data Cleaning, Analysis and Visualization System</b>
</p>

<p align="center">

![Java](https://img.shields.io/badge/Java-Swing-orange)
![Python](https://img.shields.io/badge/Python-Data%20Engine-blue)
![Pandas](https://img.shields.io/badge/Pandas-Data%20Analysis-150458)
![NumPy](https://img.shields.io/badge/NumPy-Statistics-013243)
![Matplotlib](https://img.shields.io/badge/Matplotlib-Visualization-blue)
![Seaborn](https://img.shields.io/badge/Seaborn-Data%20Visualization-4C72B0)
![License](https://img.shields.io/badge/License-MIT-green)

</p>

---

## 📌 Overview

**SmartData Analyzer** is a desktop-based data analysis application designed to simplify the process of working with CSV datasets.

The application combines a **Java Swing graphical user interface** with a **Python-based data processing engine**. Users can upload a CSV dataset and automatically perform:

* Data validation
* Dataset preview
* Missing value detection and handling
* Duplicate detection and removal
* Data cleaning
* Statistical analysis
* Correlation analysis
* Automatic chart generation
* Analytics dashboard visualization
* HTML report generation

The Java application communicates with the Python data engine using **command-line arguments and JSON responses**.

---

# ✨ Features

## 📂 CSV Dataset Upload

* Upload CSV datasets through a graphical file chooser
* Validates CSV files before processing
* Detects empty datasets
* Displays dataset information
* Shows a preview of the uploaded dataset

---

## 📊 Interactive Dashboard

The dashboard provides quick insights into the uploaded dataset.

### Dashboard KPIs

* Total Rows
* Total Columns
* Missing Values
* Duplicate Rows

---

## 🧹 Automated Data Cleaning

The system automatically performs multiple data cleaning operations.

### Cleaning Pipeline

```text
Raw CSV Dataset
        │
        ▼
Clean Column Names
        │
        ▼
Normalize Fake Null Values
        │
        ▼
Trim Whitespace
        │
        ▼
Remove Duplicate Rows
        │
        ▼
Remove Empty Rows & Columns
        │
        ▼
Detect & Standardize Date Columns
        │
        ▼
Handle Missing Values
        │
        ▼
Final Duplicate Check
        │
        ▼
Cleaned Dataset
```

### Supported Fake Null Values

The system automatically detects values such as:

```text
NA
N/A
NULL
None
Nil
-
--
?
Unknown
Not Available
```

and converts them into proper missing values.

### Automatic Missing Value Handling

| Data Type                    | Strategy                     |
| ---------------------------- | ---------------------------- |
| Numeric                      | Median                       |
| Date                         | Forward Fill + Backward Fill |
| Text / Categorical           | Most Frequent Value          |
| Completely Empty Text Column | `"Unknown"`                  |

---

# 📈 Statistical Analysis

The system automatically analyzes both numeric and categorical columns.

## 🔢 Numeric Statistics

For each numeric column:

* Count
* Mean
* Median
* Standard Deviation
* Minimum
* Maximum
* First Quartile (Q1)
* Third Quartile (Q3)

## 🏷️ Categorical Statistics

For each categorical column:

* Unique Value Count
* Most Frequent Value
* Frequency of Most Frequent Value
* Non-Null Count

---

# 🔗 Correlation Analysis

SmartData Analyzer automatically:

1. Selects numeric columns
2. Calculates the correlation matrix
3. Removes duplicate correlation pairs
4. Sorts correlations by absolute strength
5. Returns the **Top 5 strongest correlations**

---

# 📊 Data Visualization

The Python visualization engine automatically generates charts based on the dataset.

### Generated Charts

* 🔴 Missing Values Chart
* 📈 Histograms with KDE
* 📊 Categorical Bar Charts
* 🔥 Correlation Heatmap

Charts are automatically saved as **PNG files**.

---

# 📉 Analytics Dashboard

The application includes a dedicated **Analytics Dashboard** where generated charts are displayed together.

```text
Generated Charts
      │
      ▼
Analytics Dashboard
      │
 ┌──────────────┬──────────────┐
 │   Chart 1    │   Chart 2    │
 ├──────────────┼──────────────┤
 │   Chart 3    │   Chart 4    │
 └──────────────┴──────────────┘
```

This allows users to quickly explore multiple insights from the dataset in one interface.

---

# 📄 Automated HTML Report

The system can automatically generate a complete HTML report.

The report includes:

* Dataset Overview
* Original Rows
* Cleaned Rows
* Duplicate Rows Removed
* Missing Values Fixed
* Cleaning Actions Performed
* Numeric Statistics
* Categorical Statistics
* Generated Charts
* Report Generation Timestamp

Charts are embedded directly into the HTML report using **Base64 encoding**.

This makes the generated report portable and easy to open in a web browser.

---

# 🏗️ System Architecture

```text
                    ┌─────────────────────┐
                    │   Java Swing GUI    │
                    │                     │
                    │  • Dashboard        │
                    │  • CSV Upload       │
                    │  • Data Cleaning    │
                    │  • Statistics       │
                    │  • Charts           │
                    │  • Analytics        │
                    │  • Reports          │
                    └──────────┬──────────┘
                               │
                               │ CSV Path + Action
                               ▼
                    ┌─────────────────────┐
                    │ Python Data Engine  │
                    │                     │
                    │   data-engine.py    │
                    └──────────┬──────────┘
                               │
          ┌────────────────────┼────────────────────┐
          ▼                    ▼                    ▼
   ┌─────────────┐      ┌─────────────┐      ┌─────────────┐
   │ CSV Loader  │      │Data Cleaner │      │Statistics   │
   └─────────────┘      └─────────────┘      │ Engine      │
                                             └─────────────┘
                               │
                 ┌─────────────┴─────────────┐
                 ▼                           ▼
          ┌─────────────┐              ┌─────────────┐
          │Chart Engine │              │Report Engine│
          └─────────────┘              └─────────────┘
                 │                           │
                 └─────────────┬─────────────┘
                               ▼
                        JSON Response
                               │
                               ▼
                       Java Swing GUI
```

---

# 📁 Project Structure

```text
SmartData-Analyzer/
│
├── Java-Swing-GUI/
│   │
│   └── src/
│       └── Main.java
│
├── Python-Data-Engine/
│   │
│   ├── data-engine.py
│   │
│   ├── loader/
│   │   └── csv_loader.py
│   │
│   ├── cleaning/
│   │   └── cleaner.py
│   │
│   ├── analysis/
│   │   └── stats_engine.py
│   │
│   ├── charts/
│   │   └── chart_engine.py
│   │
│   └── report/
│       └── report_generator.py
│
├── README.md
└── LICENSE
```

---

# 🔄 Java–Python Integration

The Java Swing application communicates with the Python engine through the command line.

### Java sends:

```text
CSV File Path
+
Requested Action
```

Example:

```text
python data-engine.py dataset.csv clean
```

Available actions:

```text
clean
stats
charts
report
```

### Python returns:

```json
{
  "success": true,
  "rows": 1000,
  "columns": 15
}
```

The Java application uses **Gson** to parse the JSON response and display results in the graphical interface.

---

# 🛠️ Technologies Used

## Frontend / Desktop Application

* Java
* Java Swing
* Gson

## Data Processing Engine

* Python
* Pandas
* NumPy

## Data Visualization

* Matplotlib
* Seaborn

## Report Generation

* HTML
* CSS
* Base64 Image Encoding

---

# ⚙️ Installation

## 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/SmartData-Analyzer.git
cd SmartData-Analyzer
```

---

## 2. Setup Python Environment

Create a virtual environment:

```bash
python -m venv .venv
```

Activate the environment.

### Windows

```bash
.venv\Scripts\activate
```

### Linux / macOS

```bash
source .venv/bin/activate
```

Install required libraries:

```bash
pip install pandas numpy matplotlib seaborn
```

---

## 3. Add Gson Dependency

The Java application uses Gson for JSON communication.

Add the Gson library to your Java project dependencies.

---

# ▶️ How to Run

## Step 1: Configure Python Path

In `Main.java`, configure:

```java
private static final String PYTHON_PATH =
        "PATH_TO_YOUR_PYTHON_EXECUTABLE";
```

Example:

```text
C:\Project\.venv\Scripts\python.exe
```

---

## Step 2: Configure Python Engine Path

Update:

```java
private static final String ENGINE_FOLDER =
        "PATH_TO_PYTHON_DATA_ENGINE";
```

The application will locate:

```text
data-engine.py
```

inside the Python Data Engine folder.

---

## Step 3: Run the Java Application

Run:

```text
Main.java
```

The SmartData Analyzer desktop application will open.

---

# 🖥️ Application Workflow

```text
1. Launch SmartData Analyzer
            │
            ▼
2. Upload CSV Dataset
            │
            ▼
3. View Dataset Preview
            │
            ▼
4. Run Data Cleaning
            │
            ▼
5. View Cleaning Results
            │
            ▼
6. Analyze Statistics
            │
            ▼
7. Generate Charts
            │
            ▼
8. Open Analytics Dashboard
            │
            ▼
9. Generate HTML Report
```

# 🚧 Future Improvements

Possible future improvements include:

* [ ] Support Excel files
* [ ] Drag and Drop Dataset Upload
* [ ] Dark Mode
* [ ] Advanced Data Filtering
* [ ] Outlier Detection
* [ ] Machine Learning Integration
* [ ] Export Results to Excel
* [ ] Export Reports to PDF
* [ ] Interactive Charts
* [ ] Database Connectivity
* [ ] User Authentication
* [ ] Configurable Cleaning Rules
* [ ] Executable Application Packaging

---

# 🎯 Project Goals

The main goal of SmartData Analyzer is to provide a simple desktop application that allows users to perform essential data analysis tasks without manually writing Python code.

The project demonstrates the integration of:

```text
Software Engineering
        +
Java Desktop Development
        +
Python Programming
        +
Data Cleaning
        +
Statistical Analysis
        +
Data Visualization
```

---

# 👨‍💻 Developer

**Nasir Ali Shaikh**

Software Engineering Student | Java Developer | Aspiring Data Analyst

If you found this project useful, consider giving the repository a ⭐.

---

<p align="center">
  Made with ❤️ using Java, Python, Pandas, NumPy, Matplotlib and Seaborn
</p>
