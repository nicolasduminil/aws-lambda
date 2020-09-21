rm -f src/test/resources/private-key.pem src/main/resources/META-INF/public-key.pem
openssl req -x509 -newkey rsa:2048 -nodes -keyout src/test/resources/private-key.pem -out src/main/resources/META-INF/resources/public-key.pem -days 365 -subj "/C=FR/O=SimplexSoftware/CN=nduminil"
