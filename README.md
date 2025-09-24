<!--TABLE OF CONTENTS-->
<details>
  <summary>Table of contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About the project</a>
      <ul>
        <li><a href="#built-with">Built with</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li> 
  </ol>
</details>


<!--ABOUT THE PROJECT-->
## About the project


                                     mm               mm
              ##                     ##               ##    
            #######  ##    ##   m###m##  ##    ##     ##
              ##     ##    ##  ##"  "##  ##    ##     ##
              ##     ##    ##  ##    ##  ##    ##     "" 
              ##     ##    ##  ##    ##  ##    ##
              ##mmm  ##mmm###  "##mm###  ##mmm###     mm
                """    """ ""    """ ""    """ ""     ""
          

Tudu is a CLI tool for CRUD operations over tasks, formatted as JSON with an accent on efficiency of input.

<!--BUILT WITH-->
### Built with
[![Java][Java.io]][Java-url] 


<!--GETTING STARTED-->
## Getting started

<!--INSTALLATION-->
## Installation
  1. Clone the repo
  ```
  git clone https://github.com/03milosevicN/Task-tracker.git
  cd Task-tracker/
  ```
  2. Build the project
  ```
  mvn clean install  
  ```
  3. Execute
  ```
  mvn exec:java@tudu -q
  ```
  4. Checkout the manual to see how the app is used
  ```
  man
  ```
<!--ROADMAP-->
## Roadmap
- [ ] Unit and integration tests
- [ ] Add ISO 8061 date format


<!--LICENSE-->
## License
Distributed under the MIT License. See `LICENSE.txt` for more information.


<!--ACKNOWLEDGEMENTS-->
## Acknowledgements
* [JUnit](https://junit.org/junit4/)
* [Jackson](https://github.com/FasterXML/jackson)
* [Exec Maven Plugin](https://www.mojohaus.org/exec-maven-plugin/)


<!--MARKDOWN LINKS & IMAGES-->
[Java.io]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/en/
