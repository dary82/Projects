#include "MyExceptions.h"

RepositoryException::RepositoryException(const std::string message)
{
	this->message = message;
}

const char* RepositoryException::what() const noexcept
{
	return this->message.c_str();
}

ValidatorException::ValidatorException(const std::string message)
{
	this->message = message;
}

const char* ValidatorException::what() const noexcept
{
	return this->message.c_str();
}
