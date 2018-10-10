# Daily Scrum

The Daily Scrum is a tool for managing, evaluating and documenting everyday progress of the software development. 

## Getting Started

### Prerequisites

This project was generated with Java 8 with a MongoDB Database.

### Install OpenJDK 8
In a terminal:
* Install the OpenJDK 8 from a PPA repository:

>sudo add-apt-repository ppa:openjdk-r/ppa

* Update the system package cache:

>sudo apt-get update

*  Install:

>sudo apt-get install openjdk-8-jdk

* If you have more than one Java version installed on your system use the following command to switch versions:

>sudo update-alternatives --config java

### Set up MongoDB

* Import the public key used by the package management system.

>sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 9DA31620334BD75D9DCB49F368818C72E52529D4

* Create a list file for MongoDB.

Ubuntu 14.04(Trusty):
>echo "deb [ arch=amd64 ] https://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/4.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.0.list

Ubuntu 16.04(Xenial):
>echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/4.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.0.list

Ubuntu 18.04(Bionic):
>echo "deb [ arch=amd64 ] https://repo.mongodb.org/apt/ubuntu bionic/mongodb-org/4.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.0.list

* Reload local package database.

>sudo apt-get update

* Install the MongoDB packages.

>sudo apt-get install -y mongodb-org=4.0.2 mongodb-org-server=4.0.2 mongodb-org-shell=4.0.2 mongodb-org-mongos=4.0.2 mongodb-org-tools=4.0.2

* Start MongoDB.

>sudo service mongod start

* Stop MongoDB.
	
>sudo service mongod stop

* Restart MongoDB.

>sudo service mongod restart

### How it works

* Execute a Maven Build.

* Start the server.

* Run the application on the server by sending and receiving JSON data.





