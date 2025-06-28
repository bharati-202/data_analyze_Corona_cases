# COVID-19 Data Analysis and Visualization

This project provides data analysis and visualization of COVID-19 statistics using Python libraries: Pandas, Matplotlib, and Seaborn. It processes the latest COVID-19 data to display:

- Total COVID-19 cases by continent
- Top 10 countries by total deaths
- Vaccination progress over time for selected countries

## Features

- Reads and processes the [OWID COVID-19 dataset](https://github.com/owid/covid-19-data)
- Visualizes data with bar plots and line plots
- Custom color gradients for enhanced visuals
- Log scale support for large numbers

## Requirements

- Python 3.x
- pandas
- matplotlib
- seaborn
- numpy

Install dependencies with:

```sh
pip install pandas matplotlib seaborn numpy
```

## Usage

1. Download the latest `owid-covid-data.csv` from [Our World in Data](https://github.com/owid/covid-19-data/tree/master/public/data).
2. Place the CSV file in the project directory.
3. Run the script:

```sh
python corona.py
```

The script will display three plots:
- Total COVID-19 cases by continent (log scale)
- Top 10 countries by total deaths
- Vaccination progress for selected countries

## File Structure

- `corona.py` - Main script for data processing and visualization

## Example Output

![Example Plot](example_plot.png)

## License

This project is licensed under the MIT License.