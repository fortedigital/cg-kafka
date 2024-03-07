from confluent_kafka import Producer
from confluent_kafka.serialization import (
    StringSerializer,
    SerializationContext,
    MessageField,
)
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.json_schema import JSONSerializer


class User(object):
    def __init__(self, name, age):
        self.name = name
        self.age = age


def user_to_dict(user, ctx):
    return dict(name=user.name, age=user.age)


def delivery_report(err, msg):
    if err is not None:
        print("Delivery failed for User record", err)
    else:
        print("your message was successfully produced", msg)


def get_schema():
    schema_str = """
    {
      "title": "User",
      "description": "Demo",
      "type": "object",
      "properties": {
        "name": {
          "description": "Name",
          "type": "string"
        },
        "age": {
          "description": "Age",
          "type": "number",
          "exclusiveMinimum": 0
        }
      },
      "required": ["name","age"],
      "additionalProperties": true
    }
    """
    return schema_str


def get_producer():
    producer = Producer(
        {
            "bootstrap.servers": "localhost:19092",
        }
    )
    return producer


def get_schema_config():
    schema_registry_conf = {
        "url": "http://localhost:8081/",
    }
    return schema_registry_conf


def main():
    topic_name = "purchases-with-schema"
    schema = get_schema()
    schema_client = SchemaRegistryClient(get_schema_config())
    key_serializer = StringSerializer()
    value_serializer = JSONSerializer(schema, schema_client, user_to_dict)
    producer = get_producer()

    while True:
        try:
            name = input("Provide name: ")
            age = int(input("Provide age: "))
            user = User(name, age)
            producer.produce(
                topic=topic_name,
                key=key_serializer(user.name),
                value=value_serializer(
                    user, SerializationContext(topic_name, MessageField.VALUE)
                ),
                on_delivery=delivery_report,
            )

        except Exception as e:
            print("Exception is ", e)
            break
        else:
            producer.flush()


if __name__ == "__main__":
    main()