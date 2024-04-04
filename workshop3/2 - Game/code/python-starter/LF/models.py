"""Models for the game."""

import uuid

from datetime import datetime
from pydantic import BaseModel

TYPE_QUESTION = "QUESTION"
TYPE_ANSWER = "ANSWER"


class Answer(BaseModel):
    """An answer to a question."""

    questionId: str
    category: str
    teamName: str = ""
    answer: str = ""
    created: str = datetime.now().isoformat()
    messageId: str = str(uuid.uuid4())
    type: str = TYPE_ANSWER


class Question(BaseModel):
    """A question."""

    category: str
    question: str
