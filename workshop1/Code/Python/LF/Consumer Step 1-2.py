import json
from kafka import KafkaConsumer, KafkaProducer
import signal
import logging
import time 

class Killer:
    def __init__(self):
        signal.signal(signal.SIGINT, self.exit_gracefully)
        signal.signal(signal.SIGTERM, self.exit_gracefully)

        self.shutdown_signal = False

    def exit_gracefully(self, signal_no, stack_frame):
        self.shutdown_signal = True
        raise SystemExit

kafka_server = ["localhost:19092"]

topic = "purchases"

consumer = KafkaConsumer(
                         bootstrap_servers=kafka_server,
                         auto_offset_reset='earliest',
                         enable_auto_commit=False,
                         group_id='Gruppe1',
                         value_deserializer=lambda x: x.decode('utf-8')
                        )

consumer.subscribe(topic)

killer = Killer()
while not killer.shutdown_signal:
    try:
        for m in consumer:
            data = next(consumer)
            print(data)
            print(data.value)
    except SystemExit:
        time.sleep(2)
        logging.info('Shutting down...')
        print('Shutting down')
    finally:
        consumer.close()