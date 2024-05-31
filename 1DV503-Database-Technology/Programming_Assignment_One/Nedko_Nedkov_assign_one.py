from pydoc import cli
import mysql.connector
import csv
from mysql.connector import Error
import msvcrt as m


cnx = mysql.connector.connect(user = 'root',
                              password = 'root',
                              host = 'localhost'
                              )


myCursor = cnx.cursor()

dbName = 'Nedkov'


def ensureCreated(dbName, myCursor):
    myCursor.execute("SHOW DATABASES")  # Get all databases
    databaseExists = False 
    for db in myCursor:  # See if dbName is in all databases
        if db[0] == dbName.lower():
            databaseExists = True
            break
    return databaseExists


def createDatabase(dbName, myCursor):
    myCursor.execute("CREATE DATABASE " + dbName)  # Creates the database
    print('Successfully created database [' + dbName + ']')
    myCursor.execute("USE " + dbName)  # Starts using the database
    print('Using database [' + dbName + ']')


def createTables(myCursor):
    print('Creating tables:')

    myCursor.execute("CREATE TABLE Planets (\
	Name NVARCHAR(30) NOT NULL, \
	Rotation_period INT, \
	Orbital_period INT, \
	Diameter INT, \
	Climate VARCHAR(100), \
	Gravity VARCHAR(40), \
	Terrain VARCHAR(100), \
	Surface_water FLOAT, \
	Population BIGINT, \
	UNIQUE(Name), \
	PRIMARY KEY(Name));")  # Creates the table Planets with primary key Name, which is also unique
    print('\t-Sucessfully created table [Planets]')
    
    myCursor.execute("CREATE TABLE Species(\
	Name NVARCHAR(20) NOT NULL, \
	Classification VARCHAR(20), \
	Designation VARCHAR(20) NOT NULL, \
	Average_height INT, \
	Skin_colors VARCHAR(100), \
	Hair_colors VARCHAR(100), \
	Eye_colors VARCHAR(100), \
	Average_Lifespan VARCHAR(11), \
	Language VARCHAR(20), \
	Homeworld VARCHAR(30) NOT NULL, \
	UNIQUE(Name), \
	PRIMARY KEY (Name), \
	FOREIGN KEY (Homeworld) REFERENCES Planets(Name));")  # Creates the table Species with primary key Name, which is also 
                                                          #unique and foreign key Homeworld, which references Planets' name
    print('\t-Successfully created table [Species]')


def populateTables(dbName):
    print('Populating tables:')
    with open('data/planets.csv', mode ='r')as file:
        csvFile = csv.reader(file)  # Gets the records from a csv file
        next(csvFile)  # Skips the first line of the csv file (field names)
        sql = "INSERT INTO Planets (name, rotation_period, orbital_period, diameter, climate, \
        gravity, terrain, surface_water, population) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)"  # Prepare to insert records into table
        for lines in csvFile:  # Gets the values to insert
                name = lines[0] if not lines[0] == 'NA' and not lines[0] == 'N/A' else "Unnamed"  # If name is "NA" or "N/A", set to Unnamed, otherwise to name
                rotation_period = int(lines[1]) if not lines[1] == 'NA' and not lines[1] == 'N/A' else None  # -------------------------//---------------- None
                orbital_period = int(lines[2]) if not lines[2] == 'NA' and not lines[2] == 'N/A' else None  # --//--
                diameter = int(lines[3]) if not lines[3] == 'NA' and not lines[3] == 'N/A' else None  # --//--
                climate = lines[4] if not lines[4] == 'NA' and not lines[4] == 'N/A' else None  # --//--
                gravity = lines[5] if not lines[5] == 'NA' and not lines[5] == 'N/A' else None  # --//--
                terrain = lines[6] if not lines[6] == 'NA' and not lines[6] == 'N/A' else None  # --//--
                surface_water = float(lines[7]) if not lines[7] == 'NA' and not lines[7] == 'N/A' else None  # --//--
                population = lines[8] if not lines[8] == 'NA' and not lines[8] == 'N/A' else None  # --//--
                values = (name, rotation_period, orbital_period, diameter, climate, gravity, terrain, surface_water, population)  # Puts values into tuple
                myCursor.execute(sql, values)  # Inserts values into table
        cnx.commit()  # Commits changes to database
        print("\t-Successfully populated table [Planets]")

    with open('data/species.csv', mode ='r')as file:  # Same as previous
        csvFile = csv.reader(file)
        next(csvFile)
        tupleList = []
        sql = "INSERT INTO Species (name, classification, designation, average_height, skin_colors, \
        hair_colors, eye_colors, average_lifespan, language, homeworld) \
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
        for lines in csvFile:
                name = lines[0] if not lines[0] == 'NA' and not lines[0] == 'none' else "Unnamed"
                classification = lines[1] if not lines[1] == 'NA' and not lines[1] == 'none' else None
                designation = lines[2] if not lines[2] == 'NA' and not lines[2] == 'none' else None
                average_height = int(lines[3]) if not lines[3] == 'NA' and not lines[3] == 'none' else None
                skin_colors = lines[4] if not lines[4] == 'NA' and not lines[4] == 'none' else None
                hair_colors = lines[5] if not lines[5] == 'NA' and not lines[5] == 'none' else None
                eye_colors = lines[6] if not lines[6] == 'NA' and not lines[6] == 'none' else None
                average_lifespan = lines[7] if not lines[7] == 'NA' and not lines[7] == 'none' else None
                language = lines[8] if not lines[8] == 'NA' and not lines[8] == 'none' else None
                homeworld = lines[9] if not lines[9] == 'NA' and not lines[9] == 'none' else "Unnamed"
                values = (name, classification, designation, average_height, skin_colors, hair_colors, eye_colors, average_lifespan, language, homeworld)
                myCursor.execute(sql, values)
        cnx.commit()
        print("\t-Successfully populated table [Species]")


def waitKeyPress():
    m.getch()  # Pauses program until a key is pressed


def getPlanets(myCursor):
    myCursor.execute("SELECT name FROM planets")  # SQL Query
    myresult = myCursor.fetchall()  # Gets all results
    print('\n\n================ Planets ================')
    count = 0
    for x in myresult:  # Prints results 4 planets on a line
        print(x[0], end=', ')
        count += 1
        if count % 4 == 0:
            print()

    print('\n=========================================\n\nPress any key to continue...\n')
    waitKeyPress()

def getPlanetInfo(myCursor):
    planet_name = input('Select planet: ')
    myCursor.execute("SELECT * FROM planets WHERE name = \"" + planet_name + "\";")  # SQL Query
    myresult = myCursor.fetchall()  # Gets all results
    print('\n\n============= Planet [' + planet_name +'] =============')
    for x in myresult:  # Prints information about each planet
        print('    Name = ' + x[0] + '\n\
    Rotation Period = ' + str(x[1]) +'\n\
    Orbital Period = ' + str(x[2]) + '\n\
    Diameter = ' + str(x[3]) + '\n\
    Climate = ' + str(x[4]) + '\n\
    Gravity = ' + str(x[5]) + '\n\
    Terrain = ' + str(x[6]) + '\n\
    Surface Water = ' + str(x[7]) + '\n\
    Population = ' + str(x[1]))  # String parse on all but name, so None does not break the program

    print('=========================================\n\nPress any key to continue...\n')
    waitKeyPress()

def getSpeciesByHeight(myCursor):
    average_height = input('Choose average height(cm): ')
    myCursor.execute("SELECT name, average_height FROM species WHERE average_height > \"" + average_height + "\";")  # SQL Query
    myresult = myCursor.fetchall()  # Gets all results
    print('\n\n======= Species taller than [' + average_height +'] =======')
    for x in myresult:  # Prints species name and average height
        print(x[0] + '(' + str(x[1]) + ')')


    print('=========================================\n\nPress any key to continue...\n')
    waitKeyPress()

def getSpeciesClimate(myCursor):
    species_name = input('Choose species: ')
    myCursor.execute("SELECT climate FROM species AS s JOIN planets AS p ON s.homeworld = p.name WHERE s.name = \"" + species_name + "\";")  # SQL Query
    myresult = myCursor.fetchall()  # Gets all results
    print('\n\n========= Climate for [' + species_name +'] =========')
    for x in myresult:  # Prints all suitable climates for a species
        print('%5s' % '\t' + str(x[0]))

    print('=========================================\n\nPress any key to continue...\n')
    waitKeyPress()

def getLifespanByClassification(myCursor):
    myCursor.execute('SELECT classification, AVG(average_lifespan) from species GROUP BY classification ORDER BY AVG(average_lifespan) DESC;')  # SQL Query
    myresult = myCursor.fetchall()  # Gets all results
    print('\n\n=========== Average lifespans ===========')
    for x in myresult:  # Prints average lifespans for each classification
        print(str(x[0]) + ' (' + str(x[1]) + ')')

    print('==========================================\n\nPress any key to continue...\n')
    waitKeyPress()


if ensureCreated(dbName, myCursor) is False:
    createDatabase(dbName, myCursor)  # Creates database and starts using it
    createTables(myCursor)  # Creates tables Planets and Species
    populateTables(myCursor)  # Populates tables from csv files

cnx = mysql.connector.connect(user = 'root',
                              password = 'root',
                              host = 'localhost',
                              database = dbName)  # Connects to the database

while(True):
    print('============================== Options ==============================\n\
    1. List all planets\n\
    2. Search for planet details\n\
    3. Search for species with height higher than given number\n\
    4. What is the most likely desired climate of the given species?\n\
    5. What is the average lifespan per species classification?\n\
    6. Exit')
    print('=====================================================================\n')

    option = int(input("Pick an option (1-6): "))
    myCursor = cnx.cursor()    
    if option == 1:
        getPlanets(myCursor)

    if option == 2:
        getPlanetInfo(myCursor)

    if option == 3:
        getSpeciesByHeight(myCursor)

    if option == 4:
        getSpeciesClimate(myCursor)

    if option == 5:
        getLifespanByClassification(myCursor)

    if option == 6:
        break
    myCursor.close()
