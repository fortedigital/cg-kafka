from kafka import KafkaProducer
import json
from datetime import datetime


kafka_server = ['localhost:19092']

topic = 'purchases'

producer = KafkaProducer(
    bootstrap_servers=kafka_server,
    value_serializer=lambda v: json.dumps(v).encode("utf-8"),
)


data = "God dag!!"

def on_send_success(record_metadata):
    print("Melding gikk gjennom!!<3")
    print("Topic:", record_metadata.topic)
    print("Partition:", record_metadata.partition)
    print("Offset:", record_metadata.offset)
    print("Timestamp:", datetime.fromtimestamp(record_metadata.timestamp/1e3))

def on_send_error(excp):
    log.error('I am an errback', exc_info=excp)
    
    
producer.send(topic, data).add_callback(on_send_success).add_errback(on_send_error)
producer.flush()
