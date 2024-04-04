"""The Leesah quiz game client.

# 1. Ensure credential files are in the certs directory
# 2. Set `TEAM_NAME` to your preferred team name
# 3. Set `HEX_CODE` to your preferred team color
"""
from quiz_rapid import QuizRapid, Question
import os


TEAM_NAME = "Ola sitt lag"
TOPIC = "kafka-game"
HEX_CODE = "#5EE7E4"
BOOTSTRAP_SERVERS = 'kafka.daud.dev:19092-19094'
CONSUMER_GROUP_ID = 'Ola'

def handle_questions(question):
    """Call when a question is received from the stream.

    The return value is your answer to the question.
    """
    print(f"Received question: \n Categoty: {question.category} \n Question: {question.question} ")
    if question.category == "team-registration":
        return HEX_CODE
    else: 
        answer = input("Answer: ") 
        return answer


rapid = QuizRapid(team_name = TEAM_NAME, topic = TOPIC, consumer_group_id = CONSUMER_GROUP_ID, bootstrap_servers = BOOTSTRAP_SERVERS)
rapid.run(handle_questions)