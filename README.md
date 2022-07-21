# fictional-eureka

### Build Instructions

1. Clone this repository:
`$ git clone git@github.com:liyanghuang/fictional-eureka.git`
2. Download maven from https://maven.apache.org/download.cgi on Windows or run: 
`$ brew install maven` 
on Mac with Homebrew.
3. Download OpenJDK17 from https://www.azul.com/downloads/?package=jdk on Windows or run:
`$ curl -s "https://get.sdkman.io" | bash`
`$ sdk install java 17.0.1-tem` 
on Mac.
4. Check Maven's Java version with `$ mvn -v` and make sure it is using OpenJDK17.
5. build by running `$ mvn package` within the cloned directory.
