package com.github.javafaker;

import org.apache.commons.lang3.StringUtils;

public class Name {
    
    private final Faker faker;

    /**
     * Internal constructor, not to be used by clients.  Instances of {@link Name} should be accessed via
     * @param faker faker
     * {@link Faker#name()}.
     */
    protected Name(Faker faker) {
        this.faker = faker;
    }

    /**
     *      A multipart name composed of an optional prefix, a firstname and a lastname
     *      or other possible variances based on locale.  Examples:
     *
     *          James Jones Jr.
     *          Julie Johnson
     *
     * @return a random name with given and family names and an optional suffix.
     */
    public String name() {
        return faker.fakeValuesService().resolve("name.name", this, faker);
    }

    /**
     *      A multipart name composed of an optional prefix, a given and family name,
     *      another 'firstname' for the middle name and an optional suffix such as Jr. 
     *      Examples:
     *          Mrs. Ella Geraldine Fitzgerald
     *          Jason Tom Sawyer Jr.
     *          Helen Jessica Troy
     * @return a random name with a middle name component with optional prefix and suffix
     */
    public String nameWithMiddle() {
        return faker.fakeValuesService().resolve("name.name_with_middle", this, faker);
    }

    /**
     * Returns the same value as {@link #name()}
     * @return  Name#name()
     */
    public String fullName() {
        return name();
    }

    /**
     * Returns a random 'given' name such as Aaliyah, Aaron, Abagail or Abbey
     * @return a 'given' name such as Aaliyah, Aaron, Abagail or Abbey
     */
    public String firstName() {
        return faker.fakeValuesService().resolve("name.first_name", this, faker);
    }

    /**
     * Returns a random last name such as Smith, Jones or Baldwin
     * @return a random last name such as Smith, Jones or Baldwin
     */
    public String lastName() {
        return faker.fakeValuesService().resolve("name.last_name", this, faker);
    }

    /**
     * Returns a name prefix such as Mr., Mrs., Ms., Miss, or Dr.
     * @return a name prefix such as Mr., Mrs., Ms., Miss, or Dr.
     */
    public String prefix() {
        return faker.fakeValuesService().resolve("name.prefix", this, faker);
    }

    /**
     * Returns a name suffix such as Jr., Sr., I, II, III, IV, V, MD, DDS, PhD or DVM
     * @return a name suffix such as Jr., Sr., I, II, III, IV, V, MD, DDS, PhD or DVM
     */
    public String suffix() {
        return faker.fakeValuesService().resolve("name.suffix", this, faker);
    }

    /**
     *     A three part title composed of a descriptor level and job.  Some examples are :
     *         (template) {descriptor} {level} {job}
     *         Lead Solutions Specialist
     *         National Marketing Manager
     *         Central Response Liaison
     * @return a random three part job title
     */
    public String title() {
        return StringUtils.join(new String[] {
            faker.fakeValuesService().resolve("name.title.descriptor", this, faker), 
            faker.fakeValuesService().resolve("name.title.level", this, faker), 
            faker.fakeValuesService().resolve("name.title.job", this, faker) }, " ");
    }

    /**
     *     A lowercase username composed of the first_name and last_name joined with a '.'. Some examples are:
     *         (template) {@link #firstName()}.{@link #lastName()}
     *         jim.jones
     *         jason.leigh
     *         tracy.jordan
     * @return a random two part user name.
     * @see Name#firstName() 
     * @see Name#lastName()
     */
    public String username() {

        String username = StringUtils.join(new String[]{
                firstName().replaceAll("'", "").toLowerCase(),
                ".",
                lastName().replaceAll("'", "").toLowerCase()}
        );

        return StringUtils.deleteWhitespace(username);
    }
}
