from kafka import KafkaProducer
import json

kafka_server = ['localhost:19092']

topic = 'purchases'

producer = KafkaProducer(
    bootstrap_servers=kafka_server,
    value_serializer=lambda v: json.dumps(v).encode("utf-8"),
)


data = "God dag!!"


producer.send(topic, data)
producer.flush()