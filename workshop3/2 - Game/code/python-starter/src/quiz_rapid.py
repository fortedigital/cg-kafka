"""The Quiz Rapid class."""
import json
import uuid
import os

from json import JSONDecodeError
from confluent_kafka import Consumer, Producer, KafkaError, KafkaException

from .kafka_config import consumer_config, producer_config
from .models import Answer, Question, TYPE_QUESTION


class QuizRapid:
    """Mediates messages.

    To and from the quiz rapid on behalf of the quiz participant
    """

    def __init__(self,
                 team_name: str,
                 topic: str = os.getenv("QUIZ_TOPIC"),
                 consumer_group_id: str = uuid.uuid4(),
                 auto_commit: bool = False,):
        """
        Construct all the necessary attributes for the QuizRapid object.

        Parameters
        ----------
            team_name : str
                team name to filter messages on
            topic : str
                topic to produce and consume messages
            consumer_group_id : str
                the kafka consumer group id to commit offset on
            cert_file : str
                path to the certificate file
            auto_commit : bool, optional
                auto commit offset for the consumer (default is False)
        """
        self._topic = os.getenv("QUIZ_TOPIC")
        boostrapServers = os.getenv("BOOTSTRAP_SERVERS")

        consumer = Consumer(consumer_config(boostrapServers,
                                            consumer_group_id,
                                            auto_commit))
        consumer.subscribe([self._topic])

        producer = Producer(producer_config(boostrapServers))

        self.running = True
        self._team_name = team_name
        self._producer: Producer = producer
        self._consumer: Consumer = consumer

    def run(self, question_handler):
        """Run the QuizRapid."""
        print("🚀 Starting QuizRapid...")
        try:
            while self.running:
                msg = self._consumer.poll(timeout=1)
                if msg is None:
                    continue

                if msg.error():
                    self._handle_error(msg)
                else:
                    self._handle_message(msg, question_handler)

        finally:
            self.close()

    def _handle_error(self, msg):
        """Handle errors from the consumer."""
        if msg.error().code() == KafkaError._PARTITION_EOF:
            print("{} {} [{}] reached end at offset\n".
                  format(msg.topic(), msg.partition(), msg.offset()))
        elif msg.error():
            raise KafkaException(msg.error())

    def _handle_message(self, msg, question_handler):
        """Handle messages from the consumer."""
        try:
            msg = json.loads(msg.value().decode("utf-8"))
        except JSONDecodeError as e:
            print(f"error: could not parse message: {msg.value()} error: {e}")
            return

        try:
            if msg["type"] == TYPE_QUESTION:
                question = Question(category=msg['category'],
                                    question=msg['question'])
                answer_string = question_handler(question)

                if answer_string:
                    answer = Answer(questionId=msg['messageId'],
                                    category=msg['category'],
                                    teamName=self._team_name,
                                    answer=answer_string)
                    print(f"publishing answer: {answer}")
                    value = json.dumps(answer.model_dump()).encode("utf-8")
                    self._producer.produce(topic=self._topic,
                                           value=value)
        except KeyError as e:
            print(f"error: could not parse message: {msg} error: {e}")

    def close(self):
        """Close the QuizRapid."""
        print("🛑 shutting down...")
        self.running = False
        self._producer.flush()
        self._consumer.close()
        self._consumer.close()
