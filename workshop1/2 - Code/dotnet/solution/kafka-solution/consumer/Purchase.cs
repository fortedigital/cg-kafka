using Newtonsoft.Json;

public class Purchase
{
    [JsonRequired]
    [JsonProperty("Id")]
    public Guid Id { get; set; }

    [JsonProperty("Name")]
    public string Name { get; set; }

    [JsonProperty("Price")]
    public int Price { get; set; }

    [JsonProperty("Quantity")]
    public int Quantity { get; set; }

    [JsonProperty("TransactionId")]
    public Guid TransactionId { get; set; }
}
