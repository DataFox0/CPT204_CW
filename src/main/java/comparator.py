import subprocess
import random
import time
import re
import os

# Fixed list of cities
cities = [
    "New York NY", "Los Angeles CA", "Chicago IL", "Houston TX", "Phoenix AZ",
    "Philadelphia PA", "San Antonio TX", "San Diego CA", "Dallas TX", "San Jose CA",
    "Austin TX", "Jacksonville FL", "Fort Worth TX", "Columbus OH", "Charlotte NC"
]

# Fixed list of scenic spot names
attractions = [
    "Statue of Liberty", "Hollywood Sign", "Millennium Park", "NASA Space Center", "Desert Botanical Garden",
    "Liberty Bell", "The Alamo", "Balboa Park", "The Sixth Floor Museum", "Winchester Mystery House",
    "Texas State Capitol", "Jacksonville Zoo and Gardens", "Fort Worth Stockyards", "COSI", "NASCAR Hall of Fame"
]

# Generate random starting, ending, and passing nodes (make sure to select 10 scenic spots)
def generate_input_data():
    # Randomly select the starting and ending points
    start_city = random.choice(cities)
    end_city = random.choice(cities)
    
    # Randomly select 10 passing scenic spots
    selected_attractions = random.sample(attractions, 10)
    
    # Format attraction data as "Attraction1, Attraction2" line breaks
    formatted_attractions = ', '.join(selected_attractions)
    
    # Return the starting point, endpoint, and formatted passing nodes
    return start_city, end_city, formatted_attractions

# Run Java program and obtain output
def run_java_program(start_city, end_city, formatted_attractions):
    # Prepare Java commands
    command = ["java", "Main"]
    
    # Prepare data: Transfer the starting point, ending point, and passing nodes according to the input format of the console
    input_data = f"{start_city}\n{end_city}\n{formatted_attractions}\n"
    
    # Use subprocess to run Java program and pass formatted.attractions data to Java program
    result = subprocess.run(command, input=input_data, capture_output=True, text=True, check=True)

    return result.stdout

# Analyze the output of Java programs
def parse_java_output(java_output):
    # Regular expressions search for all numbers: floating-point numbers (runtime) and integers (total distance)
    pattern = r"(\d+\.\d+)|(\d+)(?=\s*miles)"  # Match floating-point numbers or integers, with integers followed by 'miles'
    numbers = re.findall(pattern, java_output)

    # Extract floating-point numbers and integers: time is a floating-point number, distance is an integer
    times_and_distances = []
    for match in numbers:
        if match[0]:
            times_and_distances.append(float(match[0]))
        elif match[1]:
            times_and_distances.append(int(match[1]))
    
    return times_and_distances

# Store runtime and results to a file
def store_results(times_and_distances):
    # Create result folder (if it doesn't exist)
    os.makedirs('result', exist_ok=True)
    
    # Store the running time and distance to the file in the result folder
    with open('result/runtime_results.txt', 'a') as runtime_file:
        runtime_file.write(f"Order Algorithm Time: {times_and_distances[0]} ms\n")
        runtime_file.write(f"Brute Force Time: {times_and_distances[2]} ms\n")
        runtime_file.write(f"DP Algorithm Time: {times_and_distances[4]} ms\n")

    with open('result/distance_results.txt', 'a') as distance_file:
        distance_file.write(f"Order Algorithm Distance: {times_and_distances[1]} miles\n")
        distance_file.write(f"Brute Force Distance: {times_and_distances[3]} miles\n")
        distance_file.write(f"DP Algorithm Distance: {times_and_distances[5]} miles\n")

# Store inconsistent test data and output
def store_inconsistent_results(start_city, end_city, formatted_attractions, java_output):
    
    os.makedirs('result/inconsistent_tests', exist_ok=True)
    
    with open('result/inconsistent_tests/inconsistent_test.txt', 'a') as file:
        file.write(f"Start City: {start_city}\n")
        file.write(f"End City: {end_city}\n")
        file.write(f"Attractions: {formatted_attractions}\n")
        file.write(f"Java Output:\n{java_output}\n")
        file.write("------\n")


def main():
    try:
        while True:
            start_city, end_city, formatted_attractions = generate_input_data()
            print(f"Start City: {start_city}")
            print(f"End City: {end_city}")
            print(f"Formatted Attractions: {formatted_attractions}")


            java_output = run_java_program(start_city, end_city, formatted_attractions)
            print("Java Output:")
            print(java_output)


            times_and_distances = parse_java_output(java_output)
            print(f"Times and Distances: {times_and_distances}")


            if times_and_distances[3] != times_and_distances[5]:
                print("Inconsistent results found. Storing data...")
                store_inconsistent_results(start_city, end_city, formatted_attractions, java_output)


            store_results(times_and_distances)
            

            time.sleep(1)

    except KeyboardInterrupt:
        print("\nComparison stopped manually by user.")

if __name__ == "__main__":
    main()
