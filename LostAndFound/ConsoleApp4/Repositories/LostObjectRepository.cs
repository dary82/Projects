using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;

namespace ConsoleApp4.Repositories
{
    public class LostObjectRepository
    {
        private SqlConnection sqlConnection;
        public LostObjectRepository()
        {
            sqlConnection = new SqlConnection(System.Configuration.ConfigurationManager.ConnectionStrings["Test"].ConnectionString);
            sqlConnection.Open();
        }

        // CreateRetrieveUpdateDelete
        //1. Create
        public void Create(LostObjectDto foundObjectDto)
        {
            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "INSERT INTO [dbo].[LostObjects]([description],[dateLost],[location],[userId],[foundObjectId],[resolved]) VALUES (@Description,@LostDate,@location,@userId,@foundObjectId,@resolved)";
            command.Parameters.Add(new SqlParameter("@Description", foundObjectDto.Description));
            command.Parameters.Add(new SqlParameter("@LostDate", foundObjectDto.LostDate));
            command.Parameters.Add(new SqlParameter("@location", foundObjectDto.Location));
            command.Parameters.Add(new SqlParameter("@userId", foundObjectDto.UserId));
            command.Parameters.Add(new SqlParameter("@foundObjectId", foundObjectDto.UserId));
            command.Parameters.Add(new SqlParameter("@resolved", foundObjectDto.Resolved));
            command.ExecuteNonQuery();
        }
       
        // 2. Retrieve - GetById() GetAll()

        public List<LostObjectDto> GetAll()
        {
            List<LostObjectDto> result = new List<LostObjectDto>();

            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "select id, description, dateLost, userId, location, foundObjectId, resolved from LostObjects";
            var reader = command.ExecuteReader();
            while(reader.Read())
            {
                

                var obj = new LostObjectDto();
                obj.Id = (int)reader[0];
                obj.Description = (string)reader[1];
                obj.LostDate = reader[2] == DBNull.Value? null : (DateTime?)reader[2];
                obj.UserId = reader[3] == DBNull.Value?  null: (int?) reader[3];
                obj.Location = reader[4] == DBNull.Value ? null : (string)reader[4];
                obj.FoundObjectId = (int)reader[5];
                obj.Resolved = (bool)reader[6];
                result.Add(obj);
            }
            reader.Close();
            return result;
        }

        public void SetLostResolved(int lostObjectId)
        {
            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "update LostObjects set Resolved = @resolved where id = @id";
            command.Parameters.Add(new SqlParameter("@resolved", true));
            command.Parameters.Add(new SqlParameter("@id", lostObjectId));
            command.ExecuteNonQuery();
        }

        public void SetFoundObject(int lostObjectId, int foundObjectId)
        {
            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "update LostObjects set foundObjectId = @foundObjectId  where id = @id";
            command.Parameters.Add(new SqlParameter("@foundObjectId", foundObjectId));
            command.Parameters.Add(new SqlParameter("@id", lostObjectId));
            command.ExecuteNonQuery();
        }

        // 4. Delete
        public void Delete(int objectId)
        {
            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "delete from [dbo].[LostObjects] where id = @objectId";

            command.Parameters.Add(new SqlParameter("@objectId", objectId));
            command.ExecuteNonQuery();
        }
    }
}