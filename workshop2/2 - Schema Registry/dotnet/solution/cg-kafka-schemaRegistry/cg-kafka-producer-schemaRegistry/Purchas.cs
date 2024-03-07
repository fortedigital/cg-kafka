namespace cg_kafka_producer_schemaRegistry;


public class Purchase
{
    public Guid Id { get; set; }

    public string Name { get; set; }

    public int Price { get; set; }

    public int Quantity { get; set; }

    public Guid TransactionId { get; set; }
}
