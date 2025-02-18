package id.kitabantu.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDateTime
import java.util.Currency

@RequiresApi(Build.VERSION_CODES.O)
object DummyData {

    val jobs = listOf(
        Job(
            title = "Lead Engineer",
            published = LocalDateTime.parse("2025-02-13T00:00:00"),
            company = "Tech Star Company",
            companyLogoUrl = "https://cdn.prod.website-files.com/6393835bb435c428e5b6a54a/6393841f01ab88522a2396b9_Techstar%20Logo%20(1)%20(1).png",
            location = "Michigan, United States",
            type = JobType.CONTRACT,
            excerpt = "Lead Engineer position for candidates residing in Michigan, USA.",
            description = listOf(
                "Lead engineering projects and teams.",
                "Collaborate with cross-functional departments.",
                "Ensure timely delivery of software solutions."
            ),
            salary = 100000,
            salaryCurrency = Currency.getInstance("USD"),
        ),
        Job(
            title = "Full-Stack Engineer",
            published = LocalDateTime.parse("2025-02-11T00:00:00"),
            company = "Tolt",
            companyLogoUrl = "https://cdn.prod.website-files.com/6329ec26d5d4133a1b0ed116/639ef54038e93fa3d9f50c28_tolt256.png",
            location = "Remote",
            type = JobType.FULL_TIME,
            excerpt = "Opportunity to work closely with the CTO in a dynamic environment.",
            description = listOf(
                "Develop and maintain web applications.",
                "Implement new features based on user feedback.",
                "Optimize application performance."
            ),
            salary = 50000,
            salaryCurrency = Currency.getInstance("USD"),
        ),
        Job(
            title = "Senior Test Automation Engineer",
            published = LocalDateTime.parse("2025-02-06T00:00:00"),
            company = "MailerLite",
            companyLogoUrl = "https://images.g2crowd.com/uploads/product/image/social_landscape/social_landscape_6f674b882a9f26544d804c2c8badd73f/mailerlite.png",
            location = "California, United States",
            type = JobType.FULL_TIME,
            excerpt = "Senior position focusing on test automation within the USA.",
            description = listOf(
                "Design and implement automated test suites.",
                "Collaborate with development teams to ensure quality.",
                "Analyze test results and report defects."
            ),
            salary = 50000,
            salaryCurrency = Currency.getInstance("USD"),
        ),
        Job(
            title = "Tech Lead",
            published = LocalDateTime.parse("2025-01-31T00:00:00"),
            company = "Zappyrent",
            companyLogoUrl = "https://media.licdn.com/dms/image/v2/C4D0BAQFdF7X2PHxRow/company-logo_200_200/company-logo_200_200/0/1672754151586/zappyrent_logo?e=2147483647&v=beta&t=CO2sjtEc8IyOwXb5YuRPgZYwATG1NB97rzzQbH-fmj0",
            location = "Milan, Italy",
            type = JobType.FULL_TIME,
            excerpt = "Technical Lead role based in Milan, overseeing development projects.",
            description = listOf(
                "Lead a team of developers.",
                "Architect and design system solutions.",
                "Ensure code quality and best practices."
            ),
            salary = 75500,
            salaryCurrency = Currency.getInstance("EUR"),
        ),
        Job(
            title = "Full Stack Ruby Engineer",
            published = LocalDateTime.parse("2025-01-31T00:00:00"),
            company = "OpenPlay",
            companyLogoUrl = "https://media.licdn.com/dms/image/v2/D560BAQEVUoINH9YnGg/company-logo_200_200/company-logo_200_200/0/1683999922488/openplay_logo?e=2147483647&v=beta&t=tNxHKW26c0k7g7IXYMWphzNNKtrIjqhqYycbWQtIQRs",
            location = "California, United States",
            type = JobType.FULL_TIME,
            excerpt = "Full Stack Engineer specializing in Ruby, located in Los Angeles.",
            description = listOf(
                "Develop and maintain Ruby on Rails applications.",
                "Work on both front-end and back-end components.",
                "Collaborate with product teams to define features."
            ),
            salary = 90000,
            salaryCurrency = Currency.getInstance("USD"),
        ),
        Job(
            title = "Mobile App Developer",
            published = LocalDateTime.parse("2025-02-15T00:00:00"),
            company = "SwiftTech",
            companyLogoUrl = "https://example.com/swifttech_logo.png",
            location = "New York, United States",
            type = JobType.FULL_TIME,
            excerpt = "Join the team to develop innovative mobile applications for iOS and Android.",
            description = listOf(
                "Develop and maintain mobile applications for iOS and Android.",
                "Collaborate with UI/UX designers to implement new features.",
                "Optimize application performance and ensure high user experience."
            ),
            salary = 80000,
            salaryCurrency = Currency.getInstance("USD"),
        ),
        Job(
            title = "Data Scientist",
            published = LocalDateTime.parse("2025-02-10T00:00:00"),
            company = "DataVision",
            companyLogoUrl = "https://example.com/datavision_logo.png",
            location = "London, United Kingdom",
            type = JobType.FULL_TIME,
            excerpt = "A position for a Data Scientist to work on cutting-edge AI and machine learning projects.",
            description = listOf(
                "Analyze large datasets to generate insights and predictions.",
                "Work with engineering teams to deploy machine learning models.",
                "Communicate findings to stakeholders and drive business decisions."
            ),
            salary = 65000,
            salaryCurrency = Currency.getInstance("GBP"),
        ),
        Job(
            title = "Cloud Architect",
            published = LocalDateTime.parse("2025-02-14T00:00:00"),
            company = "CloudInnovators",
            companyLogoUrl = "https://example.com/cloudinnovators_logo.png",
            location = "Berlin, Germany",
            type = JobType.FULL_TIME,
            excerpt = "We are looking for a Cloud Architect to lead the design and implementation of cloud infrastructure solutions.",
            description = listOf(
                "Design and implement scalable cloud infrastructure.",
                "Lead architecture decisions and collaborate with development teams.",
                "Ensure high availability, security, and disaster recovery planning."
            ),
            salary = 95000,
            salaryCurrency = Currency.getInstance("EUR"),
        )


    )
    val keyword = listOf(
        SearchSuggestion(keyword = "Teknologi"),
        SearchSuggestion(keyword = "Pendidikan"),
        SearchSuggestion(keyword = "Ekonomi"),
        SearchSuggestion(keyword = "Politik"),
        SearchSuggestion(keyword = "Olahraga"),
        SearchSuggestion(keyword = "Kesehatan"),
        SearchSuggestion(keyword = "Lingkungan"),
        SearchSuggestion(keyword = "COVID-19"),
        SearchSuggestion(keyword = "Film"),
        SearchSuggestion(keyword = "Ekspansi Bisnis"),
        SearchSuggestion(keyword = "Startup"),
        SearchSuggestion(keyword = "Perekonomian Global"),
        SearchSuggestion(keyword = "Festival Film"),

        // Topik Berita Ekonomi dan Bisnis
        SearchSuggestion(keyword = "Pasar Saham"),
        SearchSuggestion(keyword = "Inflasi"),
        SearchSuggestion(keyword = "Investasi"),
        SearchSuggestion(keyword = "Mikroekonomi"),
        SearchSuggestion(keyword = "Cryptocurrency"),
        SearchSuggestion(keyword = "Reksa Dana"),
        SearchSuggestion(keyword = "Fintech"),
        SearchSuggestion(keyword = "Perdagangan Global"),
        SearchSuggestion(keyword = "Perusahaan Unicorn"),
        SearchSuggestion(keyword = "Startup Teknologi"),

        // Topik Politik
        SearchSuggestion(keyword = "Pemilu 2024"),
        SearchSuggestion(keyword = "Kebijakan Publik"),
        SearchSuggestion(keyword = "Perang Dunia"),
        SearchSuggestion(keyword = "Hubungan Internasional"),
        SearchSuggestion(keyword = "Hak Asasi Manusia"),
        SearchSuggestion(keyword = "Demokrasi"),
        SearchSuggestion(keyword = "Isu Lingkungan Politik"),
        SearchSuggestion(keyword = "Perubahan Iklim dan Politik"),

        // Topik Olahraga
        SearchSuggestion(keyword = "Piala Dunia 2024"),
        SearchSuggestion(keyword = "Olimpiade 2024"),
        SearchSuggestion(keyword = "Sepak Bola"),
        SearchSuggestion(keyword = "Basketball"),
        SearchSuggestion(keyword = "Formula 1"),
        SearchSuggestion(keyword = "Sepak Bola Internasional"),
        SearchSuggestion(keyword = "Performa Atlet"),
        SearchSuggestion(keyword = "Kejuaraan Dunia Tenis"),

        // Topik Kesehatan
        SearchSuggestion(keyword = "Vaksin COVID-19"),
        SearchSuggestion(keyword = "Pandemi Global"),
        SearchSuggestion(keyword = "Gizi Seimbang"),
        SearchSuggestion(keyword = "Kanker"),
        SearchSuggestion(keyword = "Kesehatan Mental"),
        SearchSuggestion(keyword = "Penyakit Menular"),
        SearchSuggestion(keyword = "Penyakit Jantung"),
        SearchSuggestion(keyword = "Obesitas"),
        SearchSuggestion(keyword = "Terapi Genetik"),

        // Topik Lingkungan dan Alam
        SearchSuggestion(keyword = "Pemanasan Global"),
        SearchSuggestion(keyword = "Energi Terbarukan"),
        SearchSuggestion(keyword = "Konservasi Alam"),
        SearchSuggestion(keyword = "Perubahan Iklim"),
        SearchSuggestion(keyword = "Daur Ulang"),
        SearchSuggestion(keyword = "Pencemaran Udara"),
        SearchSuggestion(keyword = "Kerusakan Hutan"),
        SearchSuggestion(keyword = "Polusi Laut"),
        SearchSuggestion(keyword = "Keanekaragaman Hayati"),

        // Topik Budaya dan Hiburan
        SearchSuggestion(keyword = "Musik Populer"),
        SearchSuggestion(keyword = "Film Terbaru"),
        SearchSuggestion(keyword = "Industri Musik"),
        SearchSuggestion(keyword = "Buku Terlaris"),
        SearchSuggestion(keyword = "Penerbitan Buku"),
        SearchSuggestion(keyword = "Kesenian Tradisional"),
        SearchSuggestion(keyword = "Festival Musik"),
        SearchSuggestion(keyword = "Seni Visual"),
        SearchSuggestion(keyword = "Teater"),

        // Topik Sosial dan Masyarakat
        SearchSuggestion(keyword = "Kesetaraan Gender"),
        SearchSuggestion(keyword = "Kesenjangan Sosial"),
        SearchSuggestion(keyword = "Pendidikan Anak"),
        SearchSuggestion(keyword = "Pemberdayaan Perempuan"),
        SearchSuggestion(keyword = "Krisis Pengungsi"),
        SearchSuggestion(keyword = "Pemuda dan Aktivisme"),
        SearchSuggestion(keyword = "Sosial Media"),
        SearchSuggestion(keyword = "Budaya Masyarakat"),
        SearchSuggestion(keyword = "Protes Sosial"),

        // Teknologi dan Inovasi
        SearchSuggestion(keyword = "Artificial Intelligence (AI)"),
        SearchSuggestion(keyword = "Blockchain"),
        SearchSuggestion(keyword = "5G"),
        SearchSuggestion(keyword = "Internet of Things (IoT)"),
        SearchSuggestion(keyword = "Augmented Reality (AR)"),
        SearchSuggestion(keyword = "Virtual Reality (VR)"),
        SearchSuggestion(keyword = "Big Data"),
        SearchSuggestion(keyword = "Machine Learning"),
        SearchSuggestion(keyword = "Robotika"),

        // Topik Sejarah dan Budaya
        SearchSuggestion(keyword = "Sejarah Dunia"),
        SearchSuggestion(keyword = "Perang Dunia Kedua"),
        SearchSuggestion(keyword = "Revolusi Industri"),
        SearchSuggestion(keyword = "Kehidupan Kuno"),
        SearchSuggestion(keyword = "Arkeologi"),
        SearchSuggestion(keyword = "Masyarakat Adat"),
        SearchSuggestion(keyword = "Peradaban Mesir Kuno"),
        SearchSuggestion(keyword = "Artefak Sejarah"),

        // Keuangan dan Perbankan
        SearchSuggestion(keyword = "Suku Bunga Bank"),
        SearchSuggestion(keyword = "Pinjaman Mikro"),
        SearchSuggestion(keyword = "Asuransi"),
        SearchSuggestion(keyword = "Keuangan Pribadi"),
        SearchSuggestion(keyword = "Kredit Rumah"),
        SearchSuggestion(keyword = "Digital Banking"),
        SearchSuggestion(keyword = "Bank Sentral"),
        SearchSuggestion(keyword = "Finansial Inklusif"),

        // Teknologi dan Perangkat
        SearchSuggestion(keyword = "Smartphone Terbaru"),
        SearchSuggestion(keyword = "Laptop dan Gadget"),
        SearchSuggestion(keyword = "Perangkat Wearable"),
        SearchSuggestion(keyword = "Kamera Digital"),
        SearchSuggestion(keyword = "Smart Home"),
        SearchSuggestion(keyword = "Perangkat VR"),
        SearchSuggestion(keyword = "Teknologi Wearable"),
        SearchSuggestion(keyword = "Konsol Game")
    )

}

data class SearchSuggestion(
    val keyword: String
)