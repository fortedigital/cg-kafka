from kafka import KafkaProducer
import json
from time import sleep
import random

kafka_server = ['localhost:19092']

topic = 'chat'

producer = KafkaProducer(
    bootstrap_servers=kafka_server,
    value_serializer=lambda v: json.dumps(v).encode("utf-8"),
)

def on_send_success(record_metadata):
    print("Melding gikk gjennom!!<3")
    print("Topic:", record_metadata.topic)
    print("Partition:", record_metadata.partition)
    print("Offset:", record_metadata.offset)

def on_send_error(excp):
    log.error('I am an errback', exc_info=excp)

users_list = ["Daud", "Thomas", "Ola", "Erol", "Ludvig"]
items_list = ["Book", "Alarm clock", "T-shirts", "Gift card", "Batteries", "Soda", "Coffee mug"]

for i in range(15):
    user = random.choice(users_list)
    item = random.choice(items_list)
    
    producer.send(topic, item, key=user.encode('utf-8')).add_callback(on_send_success).add_errback(on_send_error)
    producer.flush()
    sleep(3)