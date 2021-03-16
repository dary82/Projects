using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;

namespace ConsoleApp4.Repositories
{
    public class FoundObjectRepository
    {
        private SqlConnection sqlConnection;
        public FoundObjectRepository()
        {
            sqlConnection = new SqlConnection(System.Configuration.ConfigurationManager.ConnectionStrings["Test"].ConnectionString);
            sqlConnection.Open();
        }

        // CreateRetrieveUpdateDelete
        //1. Create
        public void Create(FoundObjectDto foundObjectDto)
        {
            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "INSERT INTO [dbo].[FoundObjects]([description],[dateFound],[location],[userId],[resolved]) VALUES (@Description,@FoundDate,@location,@userId,@resolved)";
            command.Parameters.Add(new SqlParameter("@Description", foundObjectDto.Description));
            command.Parameters.Add(new SqlParameter("@FoundDate", foundObjectDto.FoundDate));
            command.Parameters.Add(new SqlParameter("@location", foundObjectDto.Location));
            command.Parameters.Add(new SqlParameter("@userId", foundObjectDto.UserId));
            command.Parameters.Add(new SqlParameter("@resolved", foundObjectDto.Resolved));

            command.ExecuteNonQuery();
        }
       
            

        // 2. Retrieve - GetById() GetAll()

        public List<FoundObjectDto> GetAll()
        {
            List<FoundObjectDto> result = new List<FoundObjectDto>();

            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "select id, description, dateFound, userId, location, resolved from FoundObjects";
            var reader = command.ExecuteReader();
            while(reader.Read())
            {
                

                FoundObjectDto obj = new FoundObjectDto();
                obj.Id = (int)reader[0];
                obj.Description = (string)reader[1];
                obj.FoundDate = reader[2] == DBNull.Value? null : (DateTime?)reader[2];
                obj.UserId = reader[3] == DBNull.Value?  null: (int?) reader[3];
                obj.Location = reader[4] == DBNull.Value ? null : (string)reader[4];
                obj.Resolved = (bool)reader[5];
                result.Add(obj);
            }
            reader.Close();
            return result;
        }


        // 3. Update
        public void SetFoundResolved(int foundObjectId)
        {
            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "update FoundObjects set Resolved = @resolved where id = @id";
            command.Parameters.Add(new SqlParameter("@resolved", true));
            command.Parameters.Add(new SqlParameter("@id", foundObjectId));
            command.ExecuteNonQuery();
        }

        // 4. Delete
        public void Delete(int objectId)
        {
            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "delete from [dbo].[FoundObjects] where id = @objectId";

            command.Parameters.Add(new SqlParameter("@objectId", objectId));
            command.ExecuteNonQuery();
        }
    }
}