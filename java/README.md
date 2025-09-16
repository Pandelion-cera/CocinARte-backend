# CocinARte Backend - Quick Start

## Start MySQL
```bash
mysqld_safe --datadir=/opt/homebrew/var/mysql &
```

## Start Java Application
```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
export PATH="$JAVA_HOME/bin:$PATH"
./mvnw spring-boot:run
```

App runs on: http://localhost:8080