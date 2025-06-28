import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.colors import LinearSegmentedColormap

df = pd.read_csv("owid-covid-data.csv")
df = df[["continent", "location", "date", "total_cases", "total_deaths", "people_vaccinated", "population"]]
df["date"] = pd.to_datetime(df["date"])
df = df.dropna(subset=["continent"])
print(df)

latest = df.sort_values("date").groupby("location").tail(1)
print(latest)
continent_cases = latest.groupby("continent")["total_cases"].sum().sort_values(ascending=False).reset_index()
print(continent_cases)
top10_deaths = latest.dropna(subset=["total_deaths"]).sort_values("total_deaths", ascending=False).head(10)
print(top10_deaths)
countries = ["India", "United States", "Brazil", "Germany", "United Kingdom"]
vaccine_df = df[df["location"].isin(countries)].dropna(subset=["people_vaccinated"])
print(vaccine_df)

def custom_color():
    return LinearSegmentedColormap.from_list("black_cyan", ["#03A980", "#f5f3ef86"])

black_cyan = custom_color()

def gradient_bg(ax, cmap, direction="horizontal"):
    gradient = np.linspace(0, 1, 256).reshape(-1, 1)
    if direction == "horizontal":
        gradient = gradient.T
    ax.imshow(gradient, extent=[0, 1, 0, 1], transform=ax.transAxes,
              aspect='auto', cmap=cmap, alpha=0.3, zorder=0)


fig = plt.figure(figsize=(18, 12))
gs = fig.add_gridspec(2, 2, height_ratios=[1, 1.2])

ax1 = fig.add_subplot(gs[0, 0])
gradient_bg(ax1, cmap=black_cyan)
sns.barplot(x="continent", y="total_cases", data=continent_cases, palette="coolwarm", ax=ax1)
ax1.set_title("Total COVID Cases by Continent")
ax1.set_ylabel("Total Cases (Log Scale)")
ax1.set_yscale("log")
ax1.set_xlabel("Continent")
ax1.tick_params(axis='x', rotation=45)

ax2 = fig.add_subplot(gs[0, 1])
gradient_bg(ax2, cmap=black_cyan)
sns.barplot(x="total_deaths", y="location", data=top10_deaths, palette="Set2", ax=ax2)
ax2.set_title("Top 10 Countries by Total COVID Deaths")
ax2.set_xlabel("Total Deaths")
ax2.set_ylabel("Country")

ax3 = fig.add_subplot(gs[1, :])
gradient_bg(ax3, cmap=black_cyan) 
sns.lineplot(data=vaccine_df, x="date", y="people_vaccinated", hue="location", palette="bone", ax=ax3)
ax3.set_title("Vaccination Progress Over Time")
ax3.set_xlabel("Date")
ax3.set_ylabel("People Vaccinated")
ax3.tick_params(axis='x', rotation=45)
ax3.legend(title="Country")

plt.tight_layout(pad=5.0)
plt.show()
