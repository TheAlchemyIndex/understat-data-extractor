<div align="center">
  <h3 align="center">Understat Data Extractor</h3>

  <p align="center">
    A data extraction and pipeline project written in Java, which extracts data from the Understat website related to 
    the English Premier League and writes it to csv files.
  </p>
</div>

<!-- ABOUT THE PROJECT -->
## About The Project

The Understat website provides detailed xG (expected goals) and other related statistics for the top European football
leagues.
</br>
Data is available from this source going back several seasons, as far as the 2014-15 season. To extract data for a 
particular season, update the MAIN_SEASON variable in the config.properties file (src/main/resources/config.properties) to 
reflect the target season of your choice (e.g., 2022-23). Data for previous seasons is already provided in the data folder.
</br>
This project is a Java recreation of some functionality from the Python based 
[Fantasy-Premier-League](https://github.com/vaastav/Fantasy-Premier-League) project created by 
[vaastav](https://github.com/vaastav).
</br>

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* Java - This project has been built and tested using Java 17
* Maven - This project has been built and tested using Maven 3.9.1

<!-- USAGE EXAMPLES -->
### Usage

After ensuring that Java and Maven are installed, clone this repo and run the main method in Main.java.
</br>
The config parameters for this project can be accessed in src/main/resources/config.properties. Set the MAIN_SEASON
variable to the target season of your choice (e.g., 2022-23). Setting the other season variables to the dates that 
you wish will enable csv files to be created that contain data for all seasons within the season range that you specify 
joined together (defaults are currently set to 2019 - 2023, data for those seasons must already have been extracted to 
relevant files for this to work).

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<!-- CONTACT -->
## Contact

TheAlchemyIndex - [LinkedIn](https://www.linkedin.com/in/vaughana)

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Fantasy-Premier-League - vaastav](https://github.com/vaastav/Fantasy-Premier-League)
* [Best-README-Template - othneildrew](https://github.com/othneildrew/Best-README-Template)
