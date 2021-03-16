using System;

namespace ConsoleApp4
{
    public class LostObjectDto
    {
        public int? UserId { get; set; }
        public int Id { get; set; }
        public string Description { get; set; }
        public string Location { get; set; }
        public DateTime? LostDate { get; set; }
        public int FoundObjectId { get; set; }
        public bool Resolved { get; set; }
    }
}