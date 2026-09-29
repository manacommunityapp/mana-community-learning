package com.manacommunity.academy.config;

import com.manacommunity.academy.domain.entity.*;
import com.manacommunity.academy.domain.enums.*;
import com.manacommunity.academy.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AcademyDataInitializer implements CommandLineRunner {

    private final AcademyCategoryRepository categoryRepository;
    private final AcademyInstructorRepository instructorRepository;
    private final AcademyProgramRepository programRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            seedCategories();
        }
        if (instructorRepository.count() == 0) {
            seedInstructors();
        }
        if (programRepository.count() == 0) {
            seedPrograms();
        }
    }

    private void seedCategories() {
        List<AcademyCategoryEntity> cats = List.of(
                AcademyCategoryEntity.builder().id("cat-tech").name("Technology & Coding").code("TECH").description("Software engineering, AI, cloud, web dev & coding workshops").icon("💻").displayOrder(1).active(true).build(),
                AcademyCategoryEntity.builder().id("cat-fitness").name("Fitness & Yoga").code("FITNESS").description("Yoga, Zumba, HIIT, aerobics & strength sessions").icon("🧘").displayOrder(2).active(true).build(),
                AcademyCategoryEntity.builder().id("cat-kids").name("Kids & Teens").code("KIDS").description("Coding for kids, Vedic math, storytelling, art & robotics").icon("👶").displayOrder(3).active(true).build(),
                AcademyCategoryEntity.builder().id("cat-arts").name("Arts & Crafts").code("ARTS").description("Painting, pottery, sketching, DIY crafts & photography").icon("🎨").displayOrder(4).active(true).build(),
                AcademyCategoryEntity.builder().id("cat-prof").name("Professional & Career").code("PROFESSIONAL").description("Interview prep, resume building, public speaking & leadership").icon("💼").displayOrder(5).active(true).build(),
                AcademyCategoryEntity.builder().id("cat-cooking").name("Culinary & Baking").code("COOKING").description("Baking, gourmet cooking, healthy meal prep & culinary workshops").icon("🍳").displayOrder(6).active(true).build(),
                AcademyCategoryEntity.builder().id("cat-music").name("Music & Dance").code("MUSIC").description("Guitar, classical singing, keyboard, salsa & contemporary dance").icon("🎵").displayOrder(7).active(true).build(),
                AcademyCategoryEntity.builder().id("cat-sports").name("Sports Coaching").code("SPORTS").description("Chess mastery, cricket coaching, badminton drills & table tennis").icon("🏏").displayOrder(8).active(true).build(),
                AcademyCategoryEntity.builder().id("cat-finance").name("Financial Literacy").code("FINANCE").description("Personal budgeting, stock markets, mutual funds & tax planning").icon("📊").displayOrder(9).active(true).build()
        );
        categoryRepository.saveAll(cats);
        log.info("Seeded {} Academy Categories", cats.size());
    }

    private void seedInstructors() {
        AcademyInstructorEntity sandeep = AcademyInstructorEntity.builder()
                .id("instr-sandeep")
                .communityId("comm-mana-1")
                .residentUserId("user-sandeep")
                .fullName("Sandeep Patil")
                .profession("Principal Software Engineer @ CloudTech")
                .bio("12+ years building distributed cloud microservices. Passionate about mentoring junior developers and teaching Java, Spring Boot, and AWS.")
                .profilePicUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&auto=format&fit=crop&q=80")
                .tower("Tower A")
                .flatNumber("A-204")
                .skills("Java, Spring Boot, AWS, Docker, Kubernetes, Microservices")
                .experienceYears(12)
                .status(InstructorStatus.APPROVED)
                .totalSessions(14)
                .totalLearners(112)
                .averageRating(4.9)
                .reviewCount(48)
                .approvedAt(LocalDateTime.now().minusMonths(3))
                .build();

        AcademyInstructorEntity priya = AcademyInstructorEntity.builder()
                .id("instr-priya")
                .communityId("comm-mana-1")
                .residentUserId("user-priya")
                .fullName("Priya Sharma")
                .profession("Certified Yoga & Mindfulness Instructor (RYS 500)")
                .bio("Daily morning Hatha & Vinyasa yoga practitioner helping community residents build flexibility, core strength, and mental wellness.")
                .profilePicUrl("https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=200&auto=format&fit=crop&q=80")
                .tower("Tower B")
                .flatNumber("B-501")
                .skills("Hatha Yoga, Vinyasa Flow, Pranayama, Meditation, Flexibility")
                .experienceYears(8)
                .status(InstructorStatus.APPROVED)
                .totalSessions(28)
                .totalLearners(240)
                .averageRating(5.0)
                .reviewCount(92)
                .approvedAt(LocalDateTime.now().minusMonths(4))
                .build();

        AcademyInstructorEntity arjun = AcademyInstructorEntity.builder()
                .id("instr-arjun")
                .communityId("comm-mana-1")
                .residentUserId("user-arjun")
                .fullName("Arjun Mehta")
                .profession("FIDE Rated Chess Player & Youth Coach")
                .bio("Former state champion coaching kids and adults in opening theory, tactical motifs, and endgame calculation.")
                .profilePicUrl("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200&auto=format&fit=crop&q=80")
                .tower("Tower C")
                .flatNumber("C-302")
                .skills("Chess Tactics, Opening Theory, Endgame Mastery, Mind Sports")
                .experienceYears(6)
                .status(InstructorStatus.APPROVED)
                .totalSessions(18)
                .totalLearners(95)
                .averageRating(4.8)
                .reviewCount(34)
                .approvedAt(LocalDateTime.now().minusMonths(2))
                .build();

        instructorRepository.saveAll(List.of(sandeep, priya, arjun));
        log.info("Seeded 3 Academy Instructors");
    }

    private void seedPrograms() {
        // Program 1: Java Spring Boot Workshop
        AcademyProgramEntity prog1 = AcademyProgramEntity.builder()
                .id("prog-springboot-workshop")
                .communityId("comm-mana-1")
                .instructorId("instr-sandeep")
                .instructorName("Sandeep Patil")
                .categoryId("cat-tech")
                .categoryName("Technology & Coding")
                .title("Java & Spring Boot Microservices Workshop")
                .summary("Hands-on 2-hour crash workshop building production-ready REST APIs with Spring Data JPA and PostgreSQL.")
                .description("In this interactive community workshop, we will construct a microservice from scratch. Topics include Spring Initializr, RESTful architectural design, Spring Data JPA with entity auditing, database migrations, connection pooling, and Docker deployment. Bring your laptop with JDK 17 installed!")
                .coverImageUrl("https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=600&auto=format&fit=crop&q=80")
                .learningType(LearningType.WORKSHOP)
                .level(ProgramLevel.INTERMEDIATE)
                .mode(ProgramMode.IN_PERSON)
                .location("Clubhouse Hall 1 (Projector Area)")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(2))
                .startTime("17:00")
                .durationMinutes(120)
                .capacity(20)
                .enrolledCount(18)
                .waitlistCount(2)
                .pricingType(PricingType.FREE)
                .price(BigDecimal.ZERO)
                .prerequisites("Basic understanding of Java syntax and Object-Oriented Programming.")
                .targetAudience("Software engineers, college students, and tech enthusiasts in our community.")
                .tags("Java, Spring Boot, Microservices, Backend, SQL")
                .certificateEnabled(true)
                .status(ProgramStatus.PUBLISHED)
                .averageRating(4.9)
                .reviewCount(14)
                .sessions(new ArrayList<>())
                .build();

        AcademyProgramSessionEntity s1 = AcademyProgramSessionEntity.builder()
                .id("sess-sb-1")
                .program(prog1)
                .sessionOrder(1)
                .title("Spring Boot Core, REST APIs & JPA Persistence")
                .description("Live coding session building REST endpoints with PostgreSQL integration.")
                .sessionDate(LocalDate.now().plusDays(2))
                .startTime("17:00")
                .endTime("19:00")
                .location("Clubhouse Hall 1")
                .qrCheckInToken("QR-SESS-SB-01")
                .completed(false)
                .build();
        prog1.getSessions().add(s1);

        // Program 2: Morning Yoga for Flexibility & Core
        AcademyProgramEntity prog2 = AcademyProgramEntity.builder()
                .id("prog-yoga-morning")
                .communityId("comm-mana-1")
                .instructorId("instr-priya")
                .instructorName("Priya Sharma")
                .categoryId("cat-fitness")
                .categoryName("Fitness & Yoga")
                .title("Weekend Morning Sunrise Yoga & Breathwork")
                .summary("Rejuvenate your body and mind with guided Surya Namaskars, deep stretching, and Pranayama breathing.")
                .description("Start your Sunday morning with peaceful mindfulness on the clubhouse lawn. Perfect for beginners and regular practitioners looking to relieve desk stiffness, improve flexibility, and practice guided meditation. Bring your own yoga mat and water bottle.")
                .coverImageUrl("https://images.unsplash.com/photo-1545205597-3d9d02c29597?w=600&auto=format&fit=crop&q=80")
                .learningType(LearningType.FITNESS_SESSION)
                .level(ProgramLevel.ALL_LEVELS)
                .mode(ProgramMode.IN_PERSON)
                .location("Central Clubhouse Garden Lawn")
                .startDate(LocalDate.now().plusDays(3))
                .endDate(LocalDate.now().plusDays(3))
                .startTime("06:30")
                .durationMinutes(60)
                .capacity(25)
                .enrolledCount(12)
                .waitlistCount(0)
                .pricingType(PricingType.FREE)
                .price(BigDecimal.ZERO)
                .prerequisites("No prior experience needed. Open to all age groups.")
                .targetAudience("All residents seeking health, posture alignment, and stress relief.")
                .tags("Yoga, Pranayama, Fitness, Meditation, Wellness")
                .certificateEnabled(false)
                .status(ProgramStatus.PUBLISHED)
                .averageRating(5.0)
                .reviewCount(22)
                .sessions(new ArrayList<>())
                .build();

        AcademyProgramSessionEntity s2 = AcademyProgramSessionEntity.builder()
                .id("sess-yoga-1")
                .program(prog2)
                .sessionOrder(1)
                .title("Sunrise Hatha Flow & Guided Pranayama")
                .description("60-minute revitalizing stretch and breathwork.")
                .sessionDate(LocalDate.now().plusDays(3))
                .startTime("06:30")
                .endTime("07:30")
                .location("Clubhouse Lawn")
                .qrCheckInToken("QR-SESS-YOGA-01")
                .completed(false)
                .build();
        prog2.getSessions().add(s2);

        // Program 3: Chess Strategy for Kids & Beginners
        AcademyProgramEntity prog3 = AcademyProgramEntity.builder()
                .id("prog-chess-mastery")
                .communityId("comm-mana-1")
                .instructorId("instr-arjun")
                .instructorName("Arjun Mehta")
                .categoryId("cat-sports")
                .categoryName("Sports Coaching")
                .title("Junior Chess Tactics & Endgame Mastery (4-Week Course)")
                .summary("A comprehensive 4-week course for young champions to master board vision, pins, forks, and checkmate patterns.")
                .description("Designed for children aged 7–16. Each weekly session combines 30 minutes of interactive lecture on tactical patterns followed by 45 minutes of mentored sparring games with live analysis.")
                .coverImageUrl("https://images.unsplash.com/photo-1529699211952-734e80c4d42b?w=600&auto=format&fit=crop&q=80")
                .learningType(LearningType.COURSE)
                .level(ProgramLevel.BEGINNER)
                .mode(ProgramMode.IN_PERSON)
                .location("Community Library / Activity Room")
                .startDate(LocalDate.now().plusDays(5))
                .endDate(LocalDate.now().plusDays(33))
                .startTime("16:00")
                .durationMinutes(75)
                .capacity(16)
                .enrolledCount(10)
                .waitlistCount(0)
                .pricingType(PricingType.FREE)
                .price(BigDecimal.ZERO)
                .prerequisites("Knowledge of how chess pieces move.")
                .targetAudience("Kids (Ages 7–16) and curious beginners.")
                .tags("Chess, Kids, Strategy, Mind Sports, Brain Development")
                .certificateEnabled(true)
                .status(ProgramStatus.PUBLISHED)
                .averageRating(4.8)
                .reviewCount(11)
                .sessions(new ArrayList<>())
                .build();

        for (int i = 1; i <= 4; i++) {
            AcademyProgramSessionEntity s = AcademyProgramSessionEntity.builder()
                    .id("sess-chess-" + i)
                    .program(prog3)
                    .sessionOrder(i)
                    .title("Session " + i + ": " + (i == 1 ? "Opening Principles & Center Control" : i == 2 ? "Tactical Weapons (Forks, Pins, Skewers)" : i == 3 ? "King Safety & Attacking the Castled King" : "Essential Pawn Endgames & Tournament Play"))
                    .description("Weekly lesson and sparring session.")
                    .sessionDate(LocalDate.now().plusDays(5 + (i - 1) * 7L))
                    .startTime("16:00")
                    .endTime("17:15")
                    .location("Activity Room")
                    .qrCheckInToken("QR-SESS-CHESS-0" + i)
                    .completed(false)
                    .build();
            prog3.getSessions().add(s);
        }

        programRepository.saveAll(List.of(prog1, prog2, prog3));
        log.info("Seeded 3 Academy Programs with Sessions");
    }
}
