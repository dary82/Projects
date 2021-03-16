#include "Validator.h"
#include <sstream>
#include <string>
#include <vector>
#include <math.h>

using namespace std;

std::vector<std::string> Validator::separate(const std::string& string, char delimiter)
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

Date Validator::validateDate(std::string string)
{
	int MONTH = 0;
	int DAY = 1;
	int YEAR = 2;
	std::vector<std::string> parameters = this->separate(string,'-');
	if (parameters.size() != 3)
		throw ValidatorException("Invalid date format");
	if (!this->validateIntegerDate(parameters[MONTH]) || !this->validateIntegerDate(parameters[DAY]) || !this->validateIntegerDate(parameters[YEAR]))
		throw ValidatorException("Invalid date format");
	//std::cout << stoi(parameters[MONTH]) << " " << stoi(parameters[DAY]) << " " << stoi(parameters[YEAR]) << "\n";
	return Date(stoi(parameters[MONTH]), stoi(parameters[DAY]), stoi(parameters[YEAR]));
}

void Validator::validateInteger(std::string number)
{
	std::string::const_iterator iterator = number.begin();
	while (iterator != number.end() && std::isdigit(*iterator)) ++iterator;
	if(!(!number.empty() && iterator == number.end()))
		throw ValidatorException("One of the parameters must be a positive integer");
}

int Validator::validateIntegerDate(std::string number)
{
	std::string::const_iterator iterator = number.begin();
	while (iterator != number.end() && std::isdigit(*iterator)) ++iterator;
	return !number.empty() && iterator == number.end();
}

void Validator::emptyString(std::string givenString)
{
	if (givenString == "")
		throw ValidatorException("String must not be empty!");
}

void Validator::validateParameters(std::vector<std::string> parameters)
{
	this->emptyString(parameters[PARAMETER_FIRST]);
	this->emptyString(parameters[PARAMETER_SECOND]);
	this->emptyString(parameters[PARAMETER_FIFTH]);
	this->validateInteger(parameters[PARAMETER_FOURTH]);
}
