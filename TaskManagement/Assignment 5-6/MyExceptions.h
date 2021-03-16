#pragma once
#include<exception>
#include<string>

class RepositoryException : public std::exception
{
private:
	std::string message;
public:
	RepositoryException(const std::string message);
	const char* what() const noexcept override;
};

class ValidatorException : public std::exception
{
private:
	std::string message;
public:
	ValidatorException(const std::string message);
	const char* what() const noexcept override;
};

