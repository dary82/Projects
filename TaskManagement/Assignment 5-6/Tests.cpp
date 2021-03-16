#include "Tests.h"
#include "Task.h"
#include "Repository.h"
#include "Service.h"
#include "UI.h"
#include <assert.h>
/*
void ClearFile()
{
	FileRepository repository{ "E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt" };
	std::vector<Task> tasks = repository.getAllTasks();
	for (auto i : tasks)
		repository.deleteTask(i.getTitle());
}

void Task_ValidInput_TitleIsGood()
{
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	assert(task1.getTitle() == "clean");
}

void Task_ValidInput_TypeIsGood()
{
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	assert(task1.getType() == "spilled");
}

void GetDay_ValidInput_IsGood()
{
	Date lastPerformed{ 10, 21, 1245 };
	assert(lastPerformed.getDay() == 21);
}

void GetMonth_ValidInput_IsGood()
{
	Date lastPerformed{ 10, 21, 1245 };
	assert(lastPerformed.getMonth() == 10);
}

void GetYear_ValidInput_IsGood()
{
	Date lastPerformed{ 10, 21, 1245 };
	assert(lastPerformed.getYear() == 1245);
}

void Task_ValidInput_LastPerformedIsGood()
{
	Date lastPerformed{ 10, 21, 1245 };
	Task task1{ "clean", "spilled", lastPerformed, 4, "sparkling" };
	GetDay_ValidInput_IsGood();
	GetMonth_ValidInput_IsGood();
	GetYear_ValidInput_IsGood();
}

void Task_ValidInput_LastPerformedStringIsGood()
{
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	assert(task1.getLastPerformedAsString() == "10-21-1245");
}

void Task_ValidInput_TimesPerformedIsGood()
{
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	assert(task1.getTimesPerformed() == 4);
}

void Task_ValidInput_VisionIsGood()
{
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	assert(task1.getVision() == "sparkling");
}

void TaskSetTitle_ValidInput_TitleSet()
{
	Task task{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	task.setTitle("abc");
	assert(task.getTitle() == "abc");
}

void Task_Equality_TasksAreEqual()
{
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	Task task2{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	assert(task1 == task2);
}

void Task_Assignation_TaskAssigned()
{
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	Task task2{};
	task2 = task1;
	assert(task2 == task1);
}

void Date_Equality_TheDatesAreTheSame()
{
	Date date1{ 10,21,125 };
	Date date2{ 10,21,125 };

	assert(date1 == date2);
}

void Add_ValidInput_Added()
{
	FileRepository repository{"E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt"};
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	assert(repository.add(task1)==true);
}

void Add_Duplicate_NotAdded()
{
	FileRepository repository{ "E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt" };
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };;
	assert(repository.add(task1)==false);
}

void Update_ValidInput_Updated()
{
	FileRepository repository{ "E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt" };
	Task task{ "clean", "spill", Date{10, 21, 1245}, 5, "sparkling" };
	assert(repository.update(task) == true);
}

void UpdateTask_NotExisting_NotUpdated()
{
	FileRepository repository{ "E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt" };
	Task task{ "dry", "spill", Date{10, 21, 1245}, 5, "sparkling" };
	assert(repository.update(task) == false);
}

void DeleteTask_ValidInput_Deleted()
{
	FileRepository repository{ "E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt" };
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	std::string title = "clean";
	assert(repository.deleteTask(title) == true);
}

void DeleteTask_NotExisting_NotDeleted()
{
	FileRepository repository{ "E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt" };
	Task task1{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	std::string title = "dry";
	assert(repository.deleteTask(title) == false);
}

void GetAllTasks_ExistsOneTask_ReturnsOne()
{
	FileRepository repository{ "E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt" };
	Task task{ "clean", "spilled", Date{10, 21, 1245}, 4, "sparkling" };
	repository.add(task);
	std::vector<Task> tasks = repository.getAllTasks();
	assert(tasks.size() == 1);
	repository.deleteTask("clean");
}

void AddTaskService_ValidInput_ReturnTrue()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	std::string title = "clean";
	std::string type = "spilled";
	Date lastPerformed{ 10, 21, 1245 };
	int timesPerformed = 4;
	std::string vision = "sparkling";
	assert(service.addTaskService(title, type, lastPerformed, timesPerformed, vision) == true);
}

void AddTaskService_Duplicate_ReturnFalse()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	std::string title = "clean";
	std::string type = "spilled";
	Date lastPerformed{ 10, 21, 1245 };
	int timesPerformed = 4;
	std::string vision = "sparkling";
	assert(service.addTaskService(title, type, lastPerformed, timesPerformed, vision) == false);
}

void UpdateTaskService_ValidInput_ReturnTrue()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	std::string title2 = "clean";
	std::string type2 = "asd";
	Date lastPerformed2{ 10, 21, 1245 };
	int timesPerformed2 = 5;
	std::string vision2 = "asgasc";
	assert(service.updateTaskService(title2, type2, lastPerformed2, timesPerformed2, vision2) == true);
}

void UpdateTaskService_InvalidInput_ReturnFalse()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	std::string title2 = "dry";
	std::string type2 = "asd";
	Date lastPerformed2{ 10, 21, 1245 };
	int timesPerformed2 = 5;
	std::string vision2 = "asgasc";
	assert(service.updateTaskService(title2, type2, lastPerformed2, timesPerformed2, vision2) == false);
}

void DeleteTaskService_ValidInput_ReturnTrue()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	std::string title = "clean";
	assert(service.deleteTaskService(title) == true);
}

void DeleteTaskService_InvalidInput_ReturnFalse()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	std::string title = "clean";
	std::string type = "spilled";
	Date lastPerformed{ 10, 21, 1245 };
	int timesPerformed = 4;
	std::string vision = "sparkling";
	std::string title2 = "dry";
	service.addTaskService(title, type, lastPerformed, timesPerformed, vision);
	assert(service.deleteTaskService(title2) == false);
}

void GetAllTasksService_ExistsOne_ReturnsOne()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	std::vector<Task> tasks = service.getAllTasksService();
	assert(tasks.size() == 1);
}

void UpdateIteratorService_Valid_IsSetToZero()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	service.updateIteratorService();
}

void NextService_Valid_NextTaskIsFirst()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	service.updateIteratorService();
	service.nextService();
}

void NextService_Valid_IteratorIncreased()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	std::string title = "dry";
	std::string type = "spilled";
	Date lastPerformed{ 10, 21, 1245 };
	int timesPerformed = 4;
	std::string vision = "sparkling";
	service.addTaskService(title, type, lastPerformed, timesPerformed, vision);
	service.updateIteratorService();
	service.nextService();
}

/*void SaveService_Valid_MyTasksHasSizeOne()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	service.updateIteratorService();
	service.saveService();
	assert(service.getMyListService().size() == 1);
}*/

/*void GetMyListService_Valid_IsEmpty()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	assert(service.getMyListService().size() == 0);
}*/
/*
void NextService_Invalid_ThrowsException()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	service.updateIteratorService();
	bool exceptionThrown = false;
	try
	{
		service.nextService();
	}
	catch(...)
	{
		exceptionThrown = true;
	}
	assert(exceptionThrown);
}

void SaveService_Invalid_ThrowsException()
{
	Service service;
	service.setPathService("E:\OOP\Assignment 5-6\Assignment 5-6\TestFile.txt");
	service.updateIteratorService();
	bool exceptionThrown = false;
	try
	{
		service.saveService();
	}
	catch (...)
	{
		exceptionThrown = true;
	}
	assert(exceptionThrown);
}

void CreateFile_Valid_CreatesANewFile()
{
	FileRepository repository{ "E:\OOP\Assignment 5-6\Assignment 5-6\TestNewFile.txt" };
}

void TestAll()
{
	ClearFile();
	Task_ValidInput_TitleIsGood();
	Task_ValidInput_TypeIsGood();
	Task_ValidInput_LastPerformedIsGood();
	Task_ValidInput_LastPerformedStringIsGood();
	Task_ValidInput_TimesPerformedIsGood();
	Task_ValidInput_VisionIsGood();
	TaskSetTitle_ValidInput_TitleSet();
	Task_Equality_TasksAreEqual();
	Task_Assignation_TaskAssigned();
	Date_Equality_TheDatesAreTheSame();
	Add_ValidInput_Added();
	Add_Duplicate_NotAdded();
	Update_ValidInput_Updated();
	UpdateTask_NotExisting_NotUpdated();
	DeleteTask_ValidInput_Deleted();
	DeleteTask_NotExisting_NotDeleted();
	GetAllTasks_ExistsOneTask_ReturnsOne();
	AddTaskService_ValidInput_ReturnTrue();
	AddTaskService_Duplicate_ReturnFalse();
	UpdateTaskService_ValidInput_ReturnTrue();
	UpdateTaskService_InvalidInput_ReturnFalse();
	DeleteTaskService_ValidInput_ReturnTrue();
	DeleteTaskService_InvalidInput_ReturnFalse();
	GetAllTasksService_ExistsOne_ReturnsOne();
	UpdateIteratorService_Valid_IsSetToZero();
	NextService_Valid_NextTaskIsFirst();
	NextService_Valid_IteratorIncreased();
	//SaveService_Valid_MyTasksHasSizeOne();
	//GetMyListService_Valid_IsEmpty();
	ClearFile();
	NextService_Invalid_ThrowsException();
	SaveService_Invalid_ThrowsException();
	CreateFile_Valid_CreatesANewFile();
}*/