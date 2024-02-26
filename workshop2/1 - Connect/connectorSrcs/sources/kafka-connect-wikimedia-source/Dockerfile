FROM confluentinc/cp-kafka-connect:3.2.0

WORKDIR /kafka-connect-source-wikimedia
COPY config config
COPY target target

VOLUME /kafka-connect-source-wikimedia/config
VOLUME /kafka-connect-source-wikimedia/offsets

CMD CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')" connect-standalone config/worker.properties config/WikimediaSourceConnector.properties
