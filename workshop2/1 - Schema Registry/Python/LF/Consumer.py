from confluent_kafka.serialization import SerializationContext, MessageField
from confluent_kafka.schema_registry.json_schema import JSONDeserializer
from confluent_kafka import Consumer


class User(object):
    def __init__(self, name, age):
        self.name = name
        self.age = age


def dict_to_user(obj, ctx):
    if obj is None:
        return None

    return User(obj["name"], obj["age"])


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
          "description": "age",
          "type": "number",
          "exclusiveMinimum": 0
        }
      },
      "required": ["name","age"],
      "additionalProperties": true
    }
    """
    return schema_str


def get_consumer_config():
    consumer_conf = {
        "bootstrap.servers": "localhost:19092",
        "group.id": "Gruppe1"
    }
    return consumer_conf


def main():
    topic_name = "purchases-with-schema"
    schema = get_schema()
    json_deserializer = JSONDeserializer(schema, from_dict=dict_to_user)
    consumer = Consumer(get_consumer_config())
    consumer.subscribe([topic_name])
    while True:
        try:
            msg = consumer.poll(1.0)

            if msg is None:
                continue
            user = json_deserializer(
                msg.value(), SerializationContext(msg.topic(), MessageField.VALUE)
            )
        except Exception as e:
            print("Exception in consumer is ", e)
            break
        else:
            print("The user is ", user.__dict__)

    consumer.close()


if __name__ == "__main__":
    main()