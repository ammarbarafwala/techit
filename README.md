# TechIT: A Work Order System for ECST College at CSULA

TechIT is a work order system developed by Brandon Ung, Minh Ha, Duc Le, Kevin
Castillo, and Marjorie Zelaya as their senior design project at Cal State LA.
The system is currently used by
[Technical Operations](http://www.calstatela.edu/ecst/ecst-technician-0) of the
ECST College at CSULA. More information about the project can be found [here]
(https://csns.calstatela.edu/department/cs/project/view?id=5530052).

## Software Requirements

Java and Maven are required to build the project, and MySQL and a Java EE
application server (e.g. Tomcat) are required to run it.

## Development Using Eclipse

1. Clone the project from its GitHub repository.
2. In Eclipse, select *File* -> *Import* ... -> *Existing Maven Projects* to
   import the project into Eclipse.
3. Create an empty MySQL database.
4. Populate the database using the following two SQL scripts:
   * `src/main/scripts/techit-create.sql`
   * `src/main/scripts/techit-test-insert.sql`
5. Copy `build.properties.sample` to `build.properties`, and change
   the values in `build.properties` to match your environment.
6. In Eclipse, right click on the project and select *Maven* -> *Update Project ...*
   to update the project.
7. In Eclipse, right click on the project and select *Run As* -> *Run on Server*
   to run the project.
