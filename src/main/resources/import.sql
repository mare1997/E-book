index=E\:/index
dataDir=files
spring.http.multipart.max-file-size=1MB
spring.http.multipart.max-request-size=1MB

server.port: 8080

#MySQL
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.password          = root
spring.datasource.url               = jdbc:mysql://localhost:3306/ebook?useSSL=false
spring.datasource.username          = root

logging.level.org.hibernate.SQL=debug

spring.jpa.hibernate.ddl-auto=create

spring.mvc.view.prefix: /WEB-INF/jsp/
spring.mvc.view.suffix: .jsp

logging.level.org.springframework.web=INFO
logging.level.osa.newsproject.controller=DEBUG
logging.level.org.hibernate=ERROR
logging.file=logs/spring-boot-logging.log

server.ssl.keyStoreType: PKCS12
server.ssl.key-store=data/spring.keystore
server.ssl.key-store-password=spring