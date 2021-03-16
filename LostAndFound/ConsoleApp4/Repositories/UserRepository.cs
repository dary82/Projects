using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp4.Repositories
{
    public class UserRepository
    {
        private SqlConnection sqlConnection;
        public UserRepository()
        {
            sqlConnection = new SqlConnection(System.Configuration.ConfigurationManager.ConnectionStrings["Test"].ConnectionString);
            sqlConnection.Open();
        }
        // CreateRetrieveUpdateDelete
        //1. Create

        public UserDto GetById(int id)
        {
            UserDto resultUser = new UserDto();

            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "select user_id, email, username from Users where user_id = @id";
            command.Parameters.Add(new SqlParameter("@id", id));
            var reader = command.ExecuteReader();
            while (reader.Read())
            {

                UserDto userDto = new UserDto();
                userDto.Id = (int)reader[0];
                userDto.Email = (string)reader[1];
                userDto.Name = (string)reader[2];
                resultUser = userDto;
            }
            reader.Close();
            return resultUser;
        }

        // 2. Retrieve - GetById() GetAll()

        public List<UserDto> GetAll()
        {
            List<UserDto> resultUser = new List<UserDto>();

            SqlCommand command = sqlConnection.CreateCommand();
            command.CommandText = "select id, email from Users";
            var reader = command.ExecuteReader();
            while (reader.Read())
            {


                UserDto userDto = new UserDto();
                userDto.Id = (int)reader[0];
                userDto.Name = (string)reader[1];

                resultUser.Add(userDto);
            }
            reader.Close();
            return resultUser;
        }
        
        // 3. Update

        // 4. Delete
    }
}
