# hash-master v0.1.0

# Intro

The hash-master project is intended to provide a Graphical User Interface that uses the functionality of [Hashcat](https://hashcat.net/hashcat/) as well as associated utilities.  

This project is in its infancy and has plenty of bugs.  If you spot any please submit an issue with as much detail as possible.

The easiest way to get going with this is to simply run the .jar file from a Kali or Parrot machine for the convienence of access to the built in wordlists, I run it on Pop\!\_OS and presume any Ubuntu derivative would be fine.  

You will need to have hashid, CeWL, and maskprocessor (mp64) installed.  The first two are easy to get from the repository with `apt install -y hashid && apt install -y cewl` while maskprocessor I usually grab straight from github: https://github.com/hashcat/maskprocessor/releases/tag/v0.73

## Current summary of features:

- User input hashes are analyzed at import for possible hash type using [hashid](http://psypanda.github.io/hashID/), these are not visible to the user but are used internally.  
- The 'Magic' mode attempts to crack each hash using a dictionary attack and the possible modes identified during import.  
- The normal 'Attack' shows the user the wordlist and rule that has been set.
- Users can create a custom wordlist using [CeWL](https://digi.ninja/projects/cewl.php).  
- Creating a custom hashcat rule is done with a simple tool that makes limited use of the very powerful [maskprocessor](https://hashcat.net/wiki/doku.php?id=maskprocessor). 

The ultimate objective is to separate the GUI from the back end logic as much as possible with a view towards modularity and allowing future frontend development to be independant from the core functions. 
<p>&nbsp;</p>

# Current and Planned Functionality as of 0.1.0

- [ ] Hash input
	- [x] Read from file
	- [x] Copy and Paste 
	- [ ] HashQueue.Hash extraction (i.e. user uploads encrypted zip and app extracts the hash)
	- [x] Inputs populate 'queue' table on GUI
	- [x] Auto-detects most likely hash type
- [ ] Output
	- [x] Table in GUI alongside respective hashes
	- [ ] Rainbow Table
	- [ ] CSV
	- [ ] Report
	- [ ] Greppable
- [x] Magic mode
	- [x] For a given `HashQueue.Hash` object, iterate through possible hash types and attempt to crack
- [ ] Command Builder
	- [ ] Use checkboxes to toggle command line arguments
	- [x] Use dropdowns or file chooser to select word lists
	- [ ] Command to be used is created in real time and available for copy and paste
- [x] Rules
	- [x] User can easily make and save custom rules
	- [x] Created rules are available from Command Builder
	- [x] integrate maskprocessor
- [ ] Dynamic custom wordlist attacks
	- [ ] All modes operate on-demand so not to kill disk space
	- [x] CeWL used for targeted web-scraping
	- [ ] princeprocessor for compound wordlist
	- [ ] kwprocessor for keyboard walk attacks (i.e. 1qaz!QAZ2wsx@WSX)

