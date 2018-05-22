# Property files parser
Environment management on Hybris can get complicated once you have multiple environments with multiple cluster instances, each with it's properties and different from the others. 

This program aims to help managing property files and to keep things simple.  

## How it works
The program takes as its sole input the location of the environment directories you want to analyse, then it will look for all the files with .properties extension.
After analysis is done, only properties with the sam    e value in every environment will be printed 

## How to use it
- You can load it into your favorite IDE as Maven managed project, running PropertyanalyserApplication class.
- Compile it using maven command _mvn package_
