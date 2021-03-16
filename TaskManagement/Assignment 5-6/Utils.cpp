#include "Utils.h"
#include <sstream>
#include <string>
#include <vector>
#include <math.h>
#include <fstream>

using namespace std;

std::vector<std::string> tokenize(const std::string& string, char delimiter)
{
	vector <std::string> result;
	stringstream stringStream(string);
	std::string token;
	while (getline(stringStream, token, delimiter))
	{
		result.push_back(token);
	}
	return result;
}
