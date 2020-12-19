hashcat-GUI

# Intro

The hashcat-GUI project is intended to provide a Graphical User Interface that uses the functionality of Hashcat as well as the suite of utilities often used alongside it.  A core objective is to separate the GUI from the back end logic as much as possible with the view towards modularity and allowing future GUI/WebApp development to be independant from the core functions. 
<p>&nbsp;</p>

# Functionality

- [ ] Hash input
	- [ ] Read from file
	- [ ] Copy and Paste 
	- [ ] Hash extraction (i.e. user uploads encrypted zip and app extracts the hash)
	- [ ] Inputs populate 'queue' table on GUI
	- [ ] Auto-detects most likely hash type
- [ ] Output
	- [ ] Table in GUI alongside respective hashes
	- [ ] Rainbow Table
	- [ ] CSV
	- [ ] Report
	- [ ] Greppable
- [ ] Magic mode
	- [ ] For a given `Hash` object, iterate through possible hash types and attempt to crack
- [ ] Command Builder
	- [ ] Use checkboxes to toggle command line arguments
	- [ ] Use dropdowns or file chooser to select word lists
	- [ ] Command to be used is created in real time and available for copy and paste
- [ ] Masks and Rules
	- [ ] User can easily make and save custom masks
	- [ ] User can easily make and save custom rules
	- [ ] Created masks and rules are available from Command Builder
	- [ ] integrate maskprocessor
- [ ] Dynamic custom wordlist attacks
	- [ ] All modes operate on-demand so not to kill disk space
	- [ ] CeWL used for targeted web-scraping
	- [ ] princeprocessor for compound wordlist
	- [ ] kwprocessor for keyboard walk attacks (i.e. 1qaz!QAZ2wsx@WSX)

# UML
![743310666ea88129b60915bcf100ff55.png](../_resources/4adf6b4a4c4c4eeda02e33dc9867b3e8.png)
	
	
# Class overviews
<p>&nbsp;</p>

## MainGui
- Constructed using Swing
- Multiple tabs shift user between modes
- Implemented as both a desktop app and webapp if possible

## Hash

Simple object to describe a single given hash.  Saves key steps in analysis of the hash as the application processes it.  

- Fields:
	- `String hash;` holds the input hash value  
	- `String password;` holds the plaintext password once it is cracked
	- `ArrayList<String> possibleHashType;` holds the detected possible hash type
	- `String verifiedHashType;` if a hash is cracked, this holds the verified hash type 
- Methods:
	- `toString()` returns a summary of the hash, possible hash type if uncracked, verified hash type if cracked, and the password, if cracked.  Should be one line and easily greppable
	- `setPassword(String password)` saves the password once cracked
	- `setVerifiedHashType(String hashType)` saves the hash type once confirmed
	- `setPossibleHashType(ArrayList<String> possibleHashType)` sets possible hash types

## HashQueue

This Class defines a priority queue that holds the `Hash` objects to be processed.  The constructor takes input hashes and creates and enques new `Hash` objects.  The priority is set according to time in the queue, basic FIFO implementation.  Future development may adjust this to prioritize faster hashing algorithms in instances where the queue contains `Hash` objects of different hash types.

The HashQueue contains a method that atatcks the hashes by reaching out as necessary to the utility classes.  Some of the use of utility classes, such as hash type identification, has already happend during construction.

## HashTypeIdentifier

Utility class that accepts a given Hash object, passes the Hash.hash String to `hashid` and `hash-identifier` and parses the output to create a useful list of possible hash types ordered by probability.  This list is then set within the Hash object.

## Modes (Enum)

Simple Enum that holds all the possible `hashcat` modes.  Has a method `getMode(String hashType)` which takes a given hash type and returns the hashcat mode for use to build the `HashcatCommand`.  For example, sending `getMode("MD4")` would return `-m 900`.

## HashcatCommand

Instances of this class represent a command that will be passed to the system call to `hashcat`.  The class contains fields that correspond to every possible `hashcat` command line argument.  Most will be set via user interaction with the GUI while some will be set in response to values associated with the `Hash` object that is being attacked.




