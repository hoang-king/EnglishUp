package com.example.englishup.adaptors.datasources.remote

import com.example.englishup.adaptors.datasources.local.Dto.ReadingDto
import javax.inject.Inject

interface ReadingRemoteDataSource {
    suspend fun fetchReadingList(): List<ReadingDto>
}

class ReadingRemoteDataSourceImpl @Inject constructor(
    private val apiService: ReadingApiService
) : ReadingRemoteDataSource {
    override suspend fun fetchReadingList(): List<ReadingDto> {
        // Trả về ngay danh sách bài đọc TOEIC chất lượng cao
        return listOf(
            ReadingDto(
                id = "1",
                title = "Part 7: Business Email",
                content = "Subject: Project Update\n\nDear Team,\n\nI am writing to provide a brief update on the upcoming software deployment. As discussed in yesterday's meeting, the launch has been postponed to next Friday due to unforeseen technical issues. Please ensure all your tasks are completed by Wednesday.\n\nBest regards,\nJohn Doe",
                level = "Intermediate"
            ),
            ReadingDto(
                id = "2",
                title = "Part 7: Notice/Announcement",
                content = "NOTICE TO ALL EMPLOYEES\n\nThe building management will be conducting a fire drill on Tuesday, October 15th at 10:00 AM. When the alarm sounds, please proceed to the nearest emergency exit and gather at the designated assembly point in the parking lot. Do not use the elevators.",
                level = "Beginner"
            ),
            ReadingDto(
                id = "3",
                title = "Part 7: Article",
                content = "Tech Innovations Inc. Acquires Start-Up\n\nTech Innovations Inc. announced yesterday that it has successfully acquired the promising start-up, DataStream. This strategic move is expected to significantly boost Tech Innovations' capabilities in cloud computing and data analytics. Industry experts predict this will increase their market share by 15% in the next quarter.",
                level = "Advanced"
            ),
            ReadingDto(
                id = "4",
                title = "Part 7: Advertisement",
                content = "Grand Opening: Green Leaf Cafe!\n\nJoin us this Saturday for the grand opening of Green Leaf Cafe in downtown. We offer a wide variety of organic coffees, fresh pastries, and healthy lunch options. The first 50 customers will receive a complimentary travel mug. Don't miss out on our special buy-one-get-one-free offer on all espresso drinks this weekend!",
                level = "Beginner"
            )
        )
    }
}
