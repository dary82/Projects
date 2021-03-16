#pragma once
#include<string>
#include<vector>
#include"Task.h"
#include"MyExceptions.h"

class Validator
{
private:
	std::vector<std::string> separate(const std::string& string, char delimiter);
public:
	Date validateDate(std::string string);
	void validateInteger(std::string number);
	int validateIntegerDate(std::string number);
	void emptyString(std::string givenString);
	void validateParameters(std::vector<std::string> parameters);
};

