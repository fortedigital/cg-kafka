package com.fortedigital.config

import org.apache.kafka.clients.consumer.ConsumerConfig

val consumerProps =
    mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:19092",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringDeserializer",
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.ByteArrayDeserializer",
        ConsumerConfig.GROUP_ID_CONFIG to "Admin",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false",
    )


tailrec fun <T> repeatUntilSome(block: () -> T?): T = block() ?: repeatUntilSome(block)
