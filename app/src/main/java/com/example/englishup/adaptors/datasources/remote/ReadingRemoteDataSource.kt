package com.example.englishup.adaptors.datasources.remote

import com.example.englishup.adaptors.datasources.local.Dto.ReadingDto
import com.example.englishup.entities.ReadingQuestion
import javax.inject.Inject

interface ReadingRemoteDataSource {
    suspend fun fetchReadingList(): List<ReadingDto>
}

class ReadingRemoteDataSourceImpl @Inject constructor(
    private val apiService: ReadingApiService
) : ReadingRemoteDataSource {
    override suspend fun fetchReadingList(): List<ReadingDto> {
        val result = mutableListOf<ReadingDto>()
        
        // Luôn thêm dữ liệu dự phòng chất lượng cao trước để đảm bảo màn hình có nội dung
        result.addAll(getFallbackData())

        try {
            val response = apiService.getRandomQuote(limit = 5)
            if (response.isNotEmpty()) {
                android.util.Log.d("ReadingRemoteDataSource", "fetchReadingList: Added ${response.size} quotes from API")
                result.addAll(response.map { quote ->
                    ReadingDto(
                        id = quote._id,
                        title = "Quote by ${quote.author}",
                        content = quote.content,
                        level = if (quote.length < 50) "Beginner" else if (quote.length < 150) "Intermediate" else "Advanced",
                        questions = listOf(
                            ReadingQuestion(
                                question = "Who is the author of this quote?",
                                options = listOf(quote.author, "Unknown Author", "William Shakespeare", "Albert Einstein").shuffled(),
                                correctAnswer = quote.author
                            )
                        )
                    )
                })
            }
        } catch (e: Exception) {
            android.util.Log.e("ReadingRemoteDataSource", "fetchReadingList: API failed, using fallback only", e)
        }
        
        return result
    }

    private fun getFallbackData(): List<ReadingDto> {
        return listOf(
            ReadingDto(
                id = "fb_1", title = "Part 7: Business Communication",
                content = "Subject: Project Update\n\nDear Team,\n\nThe launch has been postponed to next Friday due to technical issues. Please ensure all your tasks are completed by Wednesday.\n\nBest regards,\nJohn Doe",
                level = "Intermediate",
                questions = listOf(
                    ReadingQuestion("When was the software launch postponed to?", listOf("Monday", "Wednesday", "Next Friday", "Next Monday"), "Next Friday"),
                    ReadingQuestion("What is the reason for the delay?", listOf("Budget", "Technical issues", "Holiday", "Meeting"), "Technical issues")
                )
            ),
            ReadingDto(
                id = "fb_2", title = "Part 7: Office Notice",
                content = "Attention: The main elevator will be under maintenance on Tuesday from 9:00 AM to 1:00 PM. Please use the service stairs or the secondary elevator in the back hallway during this time.",
                level = "Beginner",
                questions = listOf(
                    ReadingQuestion("When will the elevator be maintained?", listOf("Monday", "Tuesday", "Wednesday", "Thursday"), "Tuesday"),
                    ReadingQuestion("What should employees use instead?", listOf("Front door", "Service stairs", "Roof exit", "Ladder"), "Service stairs")
                )
            ),
            ReadingDto(
                id = "fb_3", title = "Part 7: Job Advertisement",
                content = "Green Tech Solutions is looking for a Senior Software Engineer. The ideal candidate should have at least 5 years of experience with Kotlin and a strong background in mobile app development. We offer competitive salaries and flexible working hours.",
                level = "Advanced",
                questions = listOf(
                    ReadingQuestion("Which position is being advertised?", listOf("Manager", "Junior Developer", "Senior Software Engineer", "Designer"), "Senior Software Engineer"),
                    ReadingQuestion("How many years of experience are required?", listOf("2 years", "3 years", "5 years", "10 years"), "5 years")
                )
            ),
            ReadingDto(
                id = "fb_4", title = "Part 7: Travel Article",
                content = "Exploring the beauty of Da Lat: Known as the 'City of Eternal Spring', Da Lat offers a unique cool climate in Vietnam. Visitors can enjoy visiting beautiful waterfalls, flower gardens, and local coffee plantations.",
                level = "Beginner",
                questions = listOf(
                    ReadingQuestion("What is Da Lat's nickname?", listOf("City of Flowers", "City of Eternal Spring", "Mountain City", "Waterfall City"), "City of Eternal Spring"),
                    ReadingQuestion("What can visitors see in Da Lat?", listOf("Beaches", "Coffee plantations", "Ancient temples", "Snowy mountains"), "Coffee plantations")
                )
            ),
            ReadingDto(
                id = "fb_5", title = "Part 7: Recipe",
                content = "How to make a perfect omelet: Beat two eggs with a pinch of salt and pepper. Heat a non-stick pan with a small amount of butter. Pour the eggs into the pan and cook over medium heat until set. Fold and serve immediately.",
                level = "Beginner",
                questions = listOf(
                    ReadingQuestion("What should you do with the eggs first?", listOf("Boil them", "Fry them", "Beat them", "Bake them"), "Beat them"),
                    ReadingQuestion("What is used to heat the pan?", listOf("Oil", "Butter", "Water", "Milk"), "Butter")
                )
            ),
            ReadingDto(
                id = "fb_6", title = "Part 7: Product Review",
                content = "The new SmartWatch X is a great fitness companion. It has a heart rate monitor, sleep tracking, and GPS. The battery life is impressive, lasting up to 10 days on a single charge. However, the screen can be difficult to see in direct sunlight.",
                level = "Intermediate",
                questions = listOf(
                    ReadingQuestion("How long does the battery last?", listOf("1 day", "5 days", "10 days", "30 days"), "10 days"),
                    ReadingQuestion("What is a disadvantage mentioned?", listOf("Heavy", "Expensive", "No GPS", "Hard to see screen in sunlight"), "Hard to see screen in sunlight")
                )
            ),
            ReadingDto(
                id = "fb_7", title = "Part 7: Company Policy",
                content = "Employees are reminded that all business expenses must be submitted for reimbursement within 30 days of the transaction. Please attach all original receipts to your expense report.",
                level = "Intermediate",
                questions = listOf(
                    ReadingQuestion("What is the deadline for submitting expenses?", listOf("7 days", "14 days", "30 days", "60 days"), "30 days"),
                    ReadingQuestion("What must be attached to the report?", listOf("Photos", "Original receipts", "A letter", "ID card"), "Original receipts")
                )
            ),
            ReadingDto(
                id = "fb_8", title = "Part 7: Historical Fact",
                content = "The Great Wall of China is one of the world's most impressive architectural feats. Construction began as early as the 7th century BC and continued for centuries. It was built primarily to protect the Chinese Empire from nomadic invasions.",
                level = "Intermediate",
                questions = listOf(
                    ReadingQuestion("When did construction begin?", listOf("7th century BC", "1st century AD", "5th century AD", "10th century AD"), "7th century BC"),
                    ReadingQuestion("Why was it built?", listOf("For tourism", "For trade", "For protection", "For agriculture"), "For protection")
                )
            ),
            ReadingDto(
                id = "fb_9", title = "Part 7: Event Invitation",
                content = "You are cordially invited to the annual Charity Gala on Saturday, December 12th at 7:00 PM. The event will take place at the Grand Ballroom of the Royal Hotel. All proceeds will go to support local education programs.",
                level = "Beginner",
                questions = listOf(
                    ReadingQuestion("When is the event?", listOf("Friday", "Saturday", "Sunday", "Monday"), "Saturday"),
                    ReadingQuestion("Where will it be held?", listOf("At a park", "At the Royal Hotel", "At a school", "At a restaurant"), "At the Royal Hotel")
                )
            ),
            ReadingDto(
                id = "fb_10", title = "Part 7: Technology News",
                content = "SpaceX successfully launched another batch of Starlink satellites yesterday. The mission aim is to provide high-speed internet to remote areas across the globe. The reusable Falcon 9 rocket landed safely on a droneship in the Atlantic Ocean.",
                level = "Advanced",
                questions = listOf(
                    ReadingQuestion("What was launched?", listOf("A space station", "Starlink satellites", "A telescope", "A rover"), "Starlink satellites"),
                    ReadingQuestion("Where did the rocket land?", listOf("On Mars", "In the ocean", "At the launch pad", "On a droneship"), "On a droneship")
                )
            )
        )
    }
}
