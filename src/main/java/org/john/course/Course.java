package org.john.course;

public record Course(
        String index,
        String title,
        String section,
        String classNumber,
        String availableSeats,
        String status,
        String component,
        String location,
        String days,
        String time,
        String instructor
) {
    public static class Builder {
        private String index;
        private String title;
        private String section;
        private String classNumber;
        private String availableSeats;
        private String status;
        private String component;
        private String location;
        private String days;
        private String time;
        private String instructor;

        public void index(String index) {
            this.index = index;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder section(String section) {
            this.section = section;
            return this;
        }

        public Builder classNumber(String classNumber) {
            this.classNumber = classNumber;
            return this;
        }

        public Builder availableSeats(String availableSeats) {
            this.availableSeats = availableSeats;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder component(String component) {
            this.component = component;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder days(String days) {
            this.days = days;
            return this;
        }

        public Builder time(String time) {
            this.time = time;
            return this;
        }

        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }

        public Course build() {
            return new Course(
                    index,
                    title,
                    section,
                    classNumber,
                    availableSeats,
                    status,
                    component,
                    location,
                    days,
                    time,
                    instructor
            );
        }
    }
}