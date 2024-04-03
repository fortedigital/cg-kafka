"""Create configuration for Kafka clients."""


def base_config(boostrapServers: str) -> dict:
    """Create a base configuration for Kafka clients."""
    return {
        "bootstrap.servers": boostrapServers,
        "security.protocol": "PLAINTEXT",
    }


def consumer_config(boostrapServers: str, group_id: str, auto_commit: bool):
    """Create a configuration for Kafka consumers."""
    config = base_config(boostrapServers)
    return config | {
        "group.id": group_id,
        "auto.offset.reset": "earliest",
        "enable.auto.commit": str(auto_commit)
    }


def producer_config(boostrapServers: str) -> dict:
    """Create a configuration for Kafka producers."""
    return base_config(boostrapServers) | {
        "broker.address.family": "v4"
    }
