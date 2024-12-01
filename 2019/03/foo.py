from collections import defaultdict


class Segment(object):
    DIRECTIONS = ['L', 'R', 'U', 'D']
    LEFT, RIGHT, UP, DOWN = DIRECTIONS

    @classmethod
    def from_string(cls, s):
        return cls(s[0], s[1:])

    def __init__(self, direction, distance):
        self.direction = direction
        self.distance = int(distance)

    def next_coordinate(self, coordinates):
        x, y = coordinates

        if self.direction == self.LEFT:
            return (x - 1, y)

        if self.direction == self.RIGHT:
            return (x + 1, y)

        if self.direction == self.UP:
            return (x, y + 1)

        if self.direction == self.DOWN:
            return (x, y - 1)


class Grid(object):
    """
    ...........  ...........
    ...........  .+-----+...
    ...........  .|.....|...
    ....+----+.  .|..+--X-+.
    ....|....|.  .|..|..|.|.
    ....|....|.  .|.-X--+.|.
    ....|....|.  .|..|....|.
    .........|.  .|.......|.
    .o-------+.  .o-------+.
    .012.....8.  ...........
    """

    def __init__(self):
        self.points = defaultdict(list)
        self.steps = defaultdict(dict)
        self.routes = []
        self.reset_position()

    def reset_position(self):
        self.current_position = (0, 0)

    def add(self, route):
        # pass
        self.routes.append(route)

        idx = self.routes.index(route)

        segments = [
            Segment.from_string(s)
            for s in route.split(',')
        ]

        steps_taken = 0

        for s in segments:
            for _ in range(s.distance):
                steps_taken += 1

                next_coordinate = s.next_coordinate(self.current_position)

                self.points[next_coordinate].append(idx)

                try:
                    self.steps[next_coordinate][idx].append(steps_taken)
                except KeyError:
                    self.steps[next_coordinate][idx] = [steps_taken]

                self.current_position = next_coordinate

        self.reset_position()

    def get_intersections(self):
        for coordinates, visitors in self.points.items():
            if len(set(visitors)) > 1:
                yield coordinates

    @property
    def intersections(self):
        return list(self.get_intersections())

    def distance_from_center(self, coordinate):
        return abs(0 - coordinate[0]) + abs(0 - coordinate[1])

    def intersections_with_combined_steps(self):
        for coordinates in self.intersections:
            steps = self.steps[coordinates]

            x = [
                min(occurances)
                for idx, occurances in steps.items()
            ]

            yield {
                'coordinates': coordinates,
                'steps_taken': sum(x),
                'distance': self.distance_from_center(coordinates)
            }

    @property
    def closest_point_distance(self):
        return min([
            self.distance_from_center(c)
            for c in self.intersections
        ])

    @property
    def closest_point_by_steps(self):
        data = self.intersections_with_combined_steps()
        closest = sorted(data, key=lambda el: el['steps_taken'])[0]
        return closest.get('steps_taken')
