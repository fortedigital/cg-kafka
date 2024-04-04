namespace kafka_game.models;

public class Questions
{
    public string type { get; set; }
    public Guid messageId { get; set; }
    public string question {  get; set; }
    public string category { get; set; }
    public DateTime created { get; set; }
}
