namespace cg_kafka_wikimedia_consumer;

public class RecentChangeData
{
    public Meta? meta { get; set; }
    public string? id { get; set; }
    public string? type { get; set; }
    public long? @namespace { get; set; } // Use '@' to avoid reserved keyword conflict
    public string title { get; set; }
    public string? title_url { get; set; }
    public string? comment { get; set; }
    public long? timestamp { get; set; }
    public string? user { get; set; }
    public bool? bot { get; set; }
    public string? notify_url { get; set; }
    public string? server_url { get; set; }
    public string? server_name { get; set; }
    public string? server_script_path { get; set; }
    public string? wiki { get; set; }
    public string? parsedcomment { get; set; }

    // for type: edit
    public bool? minor { get; set; }
    public bool patrolled { get; set; }
    public Length? length { get; set; }
    public Revision? revision { get; set; }

    // for type: log
    public long? log_id { get; set; }
    public string? log_type { get; set; }
    public string? log_action { get; set; }
    public object? log_param { get; set; }
    public string? log_action_comment { get; set; }

    public override string ToString()
    {
        return $"RecentChangeData: [{id}] - {title} ({wiki}) - {type} by {user}";
    }
}

public class Length
{
    public long? old { get; set; }
    public long? @new { get; set; }
}

public class Revision
{
    public long? old { get; set; }
    public long? @new { get; set; }
}

public class Meta
{
    public string uri { get; set; }
    public string request_id { get; set; }
    public string id { get; set; }
    public string dt { get; set; }
    public string domain { get; set; }
    public string stream { get; set; }
    public string topic { get; set; }
    public int partition { get; set; }
    public long offset { get; set; }
}