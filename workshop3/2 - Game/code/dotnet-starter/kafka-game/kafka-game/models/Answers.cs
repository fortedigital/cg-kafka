namespace kafka_game.models;

public class Answers
{
    public string type { get; set; }
    public Guid questionId { get; set; }
    public string category { get; set; }
    public string teamName { get; set; }
    public string answer { get; set; }
    public Guid messageId { get; set; }
    public DateTime created { get; set; }
}
