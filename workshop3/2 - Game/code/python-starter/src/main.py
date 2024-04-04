"""The Leesah quiz game client.

# 1. Ensure credential files are in the certs directory
# 2. Set `TEAM_NAME` to your preferred team name
# 3. Set `HEX_CODE` to your preferred team color
"""
from quiz_rapid import QuizRapid, Question
import os


TEAM_NAME = ""
TOPIC = ""
HEX_CODE = ""
BOOTSTRAP_SERVERS = ""
CONSUMER_GROUP_ID = ""

def handle_questions(question):
    """Call when a question is received from the stream.
    
    The return value is your answer to the question.
    """
    print(f"Received question: \n Categoty: {question.category} \n Question: {question.question} ")
    if question.category == "team-registration":
        return HEX_CODE


rapid = QuizRapid(team_name = TEAM_NAME, topic = TOPIC, consumer_group_id = CONSUMER_GROUP_ID, bootstrap_servers = BOOTSTRAP_SERVERS)
rapid.run(handle_questions)